package nsbm.dea.admin.tokens;

import java.time.Clock;
import java.time.Instant;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.github.f4b6a3.ulid.UlidCreator;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import nsbm.dea.admin.config.Env;
import nsbm.dea.admin.connections.Redis;
import nsbm.dea.admin.errors.UnauthorizedException;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class AccessToken {
  private static final Algorithm algorithm = Algorithm.HMAC256(Env.getAccessTokenSecret());
  private static final String iss = "dea";

  private long iat;
  private long nbf;
  private long exp;
  private String ulid;
  private String sub;
  private String refreshTokenId;

  private String token;

  public static String getKeyForRedis(String key) {
    return String.format("admin_access_token-%s", key);
  }

  public String generate(String id, String refreshTokenId) throws UnauthorizedException {
    long now = Instant.now(Clock.systemUTC()).getEpochSecond();

    this.ulid = UlidCreator.getUlid().toString();
    this.iat = now;
    this.nbf = now;
    this.exp = now + Env.getAccessTokenExp();
    this.sub = id;
    this.refreshTokenId = refreshTokenId;

    try (JedisPool pool = Redis.getPool()) {
      try (Jedis jedis = pool.getResource()) {
        String previousAccessToken = jedis.get(RefreshToken.getKeyForRedis(this.refreshTokenId));
        if (previousAccessToken == null) {
          throw new UnauthorizedException(
              String.format("there is no refresh token in redis with the Id : %s", this.refreshTokenId));
        }
        if (previousAccessToken != "default") {
          jedis.del(previousAccessToken);
        }

        String value = jedis.get(RefreshToken.getKeyForRedis(this.refreshTokenId));
        if (value == null) {
          throw new UnauthorizedException(
              String.format("there is no refresh token with the Id : %s", this.refreshTokenId));
        }
        long ttl = jedis.ttl(RefreshToken.getKeyForRedis(this.refreshTokenId));
        if (value != "default") {
          jedis.del(value);
        }

        jedis.setex(RefreshToken.getKeyForRedis(this.refreshTokenId), ttl, this.ulid);
        jedis.setex(AccessToken.getKeyForRedis(this.ulid), Env.getAccessTokenExp(), id);
      }
    }

    this.token = JWT.create().withIssuer(AccessToken.iss).withClaim("sub", this.sub).withClaim("token_id", this.ulid)
        .withClaim("refresh_token_id", this.refreshTokenId)
        .withClaim("iat", this.iat)
        .withClaim("nbf", this.nbf)
        .withClaim("exp", this.exp)
        .sign(algorithm);
    return this.token;
  }

  public boolean isValid(String token) {
    try {
      JWTVerifier verifier = JWT.require(algorithm).withIssuer(AccessToken.iss).build();

      DecodedJWT jwt = verifier.verify(token);
      this.ulid = jwt.getClaim("token_id").asString();
      this.sub = jwt.getClaim("sub").asString();
      this.refreshTokenId = jwt.getClaim("refresh_token_id").asString();

      try (JedisPool pool = Redis.getPool()) {
        try (Jedis jedis = pool.getResource()) {
          String value = jedis.get(AccessToken.getKeyForRedis(this.ulid));
          if (value == null) {
            return false;
          }
          if (!value.equals(this.sub)) {
            return false;
          }

          String atk = jedis.get(RefreshToken.getKeyForRedis(this.refreshTokenId));
          if (atk == null) {
            return false;
          }
          if (!this.ulid.equals(atk)) {
            return false;
          }
        }

        this.token = token;
        return true;
      }
    } catch (JWTVerificationException e) {
      System.err.println(e.getMessage());
      return false;
    }
  }

  public void delete(String token) throws UnauthorizedException {
    if (!this.isValid(token)) {
      throw new UnauthorizedException("access token is not valid");
    }

    try (JedisPool pool = Redis.getPool()) {
      try (Jedis jedis = pool.getResource()) {
        jedis.del(AccessToken.getKeyForRedis(this.ulid));
      }
    }
  }

  public void cookie(HttpServletResponse response) {
    Cookie cookie = new Cookie("access_token", this.token);
    cookie.setMaxAge(Math.toIntExact(Env.getAccessTokenExp()));
    cookie.setDomain(Env.getDomain());
    cookie.setPath("/");
    cookie.setSecure(Env.getEnv() == "PROD" ? false : true);
    cookie.setHttpOnly(true);

    response.addCookie(cookie);
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

  public String getToken() {
    return token;
  }
}
