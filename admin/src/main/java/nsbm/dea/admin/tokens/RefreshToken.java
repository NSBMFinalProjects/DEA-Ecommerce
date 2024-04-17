package nsbm.dea.admin.tokens;

import java.time.Clock;
import java.time.Instant;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.github.f4b6a3.ulid.UlidCreator;

import nsbm.dea.admin.config.Env;
import nsbm.dea.admin.connections.Redis;
import nsbm.dea.admin.errors.UnauthorizedException;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RefreshToken {
  private static final Algorithm algorithm = Algorithm.HMAC256(Env.getRefreshTokenSecret());
  private static final String iss = "dea";

  private long iat;
  private long nbf;
  private long exp;
  private String ulid;
  private String sub;

  public static String getKeyForRedis(String key) {
    return String.format("admin_refresh_token-%s", key);
  }

  public String generate(String id) {
    long now = Instant.now(Clock.systemUTC()).getEpochSecond();

    this.ulid = UlidCreator.getUlid().toString();
    this.iat = now;
    this.nbf = now;
    this.exp = now + Env.getRefreshTokenExp();
    this.sub = id;

    try (JedisPool pool = Redis.getPool()) {
      try (Jedis jedis = pool.getResource()) {
        jedis.setex(RefreshToken.getKeyForRedis(this.ulid), Env.getRefreshTokenExp(), "default");
      }
    }

    return JWT.create().withIssuer(RefreshToken.iss).withClaim("sub", this.sub).withClaim("token_id", this.ulid)
        .withClaim("iat", this.iat)
        .withClaim("nbf", this.nbf)
        .withClaim("exp", this.exp)
        .sign(algorithm);
  }

  public boolean isValid(String token) {
    try {
      JWTVerifier verifier = JWT.require(algorithm).withIssuer(RefreshToken.iss).build();

      DecodedJWT jwt = verifier.verify(token);
      this.ulid = jwt.getClaim("token_id").asString();
      this.sub = jwt.getClaim("sub").asString();

      try (JedisPool pool = Redis.getPool()) {
        try (Jedis jedis = pool.getResource()) {
          String value = jedis.get(RefreshToken.getKeyForRedis(this.ulid));
          if (value == null) {
            return false;
          }
        }
      }

      return true;
    } catch (JWTVerificationException e) {
      System.err.println(e.getMessage());
      return false;
    }
  }

  public void delete(String token) throws UnauthorizedException {
    if (!this.isValid(token)) {
      throw new UnauthorizedException("refresh token is not valid");
    }

    try (JedisPool pool = Redis.getPool()) {
      try (Jedis jedis = pool.getResource()) {
        String rtk = jedis.get(RefreshToken.getKeyForRedis(this.ulid));
        if (rtk == null) {
          throw new UnauthorizedException("refresh token is not valid");
        }
        if (rtk != "default") {
          jedis.del(rtk);
        }
        jedis.del(RefreshToken.getKeyForRedis(this.ulid));
      }
    }
  }

  public static String getIss() {
    return iss;
  }

  public long getIat() {
    return iat;
  }

  public long getNbf() {
    return nbf;
  }

  public long getExp() {
    return exp;
  }

  public String getUlid() {
    return ulid;
  }

  public String getSub() {
    return sub;
  }
}
