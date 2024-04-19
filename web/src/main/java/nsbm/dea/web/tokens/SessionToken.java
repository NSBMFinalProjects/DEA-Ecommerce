package nsbm.dea.web.tokens;

import java.time.Clock;
import java.time.Instant;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.github.f4b6a3.ulid.UlidCreator;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import nsbm.dea.web.config.Env;
import nsbm.dea.web.models.User;

public class SessionToken {
  private static final Algorithm algorithm = Algorithm.HMAC256(Env.getSessionTokenSecret());
  private static final String iss = "dea";
  private static final Gson gson = new Gson();

  private long iat;
  private long nbf;
  private long exp;
  private String ulid;
  private User user;

  private String token;

  public String generate(User user) {
    long now = Instant.now(Clock.systemUTC()).getEpochSecond();

    this.ulid = UlidCreator.getUlid().toString();
    this.iat = now;
    this.nbf = now;
    this.exp = now + Env.getSessionTokenExp();
    this.user = user;

    JsonObject object = new JsonObject();
    object.addProperty("id", this.user.getId());
    object.addProperty("email", this.user.getEmail());
    object.addProperty("username", this.user.getUsername());
    object.addProperty("name", this.user.getName());
    object.addProperty("photo_url", this.user.getPhotoURL());

    this.token = JWT.create().withIssuer(SessionToken.iss).withClaim("sub", this.user.getId())
        .withClaim("token_id", this.ulid)
        .withClaim("iat", this.iat)
        .withClaim("user", object.toString())
        .withClaim("nbf", this.nbf)
        .withClaim("exp", this.exp)
        .sign(algorithm);
    return this.token;
  }

  public boolean isValid(String token) {
    try {
      JWTVerifier verifier = JWT.require(algorithm).withIssuer(SessionToken.iss).build();

      DecodedJWT jwt = verifier.verify(token);
      this.ulid = jwt.getClaim("token_id").asString();
      JsonObject payload = gson.fromJson(jwt.getClaim("user").asString(), JsonObject.class);

      this.user = new User();
      this.user.setId(payload.get("id").getAsString());
      this.user.setUsername(payload.get("username").getAsString());
      this.user.setEmail(payload.get("email").getAsString());
      this.user.setName(payload.get("name").getAsString());
      this.user.setPhotoURL(payload.get("photo_url").getAsString());

      this.token = token;
      return true;
    } catch (JWTVerificationException e) {
      System.err.println(e.getMessage());
      return false;
    }
  }

  public void cookie(HttpServletResponse response) {
    Cookie cookie = new Cookie("session", this.token);
    cookie.setMaxAge(Math.toIntExact(Env.getSessionTokenExp()));
    cookie.setDomain(Env.getDomain());
    cookie.setPath("/");
    cookie.setSecure(Env.getEnv() == "PROD" ? false : true);
    cookie.setHttpOnly(false);

    response.addCookie(cookie);
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

  public User getUser() {
    return user;
  }

  public String getToken() {
    return token;
  }
}
