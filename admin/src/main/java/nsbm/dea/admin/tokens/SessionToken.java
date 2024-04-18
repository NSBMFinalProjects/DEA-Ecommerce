package nsbm.dea.admin.tokens;

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
import nsbm.dea.admin.config.Env;
import nsbm.dea.admin.model.Admin;

public class SessionToken {
  private static final Algorithm algorithm = Algorithm.HMAC256(Env.getSessionTokenSecret());
  private static final String iss = "dea";
  private static final Gson gson = new Gson();

  private long iat;
  private long nbf;
  private long exp;
  private String ulid;
  private Admin admin;

  private String token;

  public String generate(Admin admin) {
    long now = Instant.now(Clock.systemUTC()).getEpochSecond();

    this.ulid = UlidCreator.getUlid().toString();
    this.iat = now;
    this.nbf = now;
    this.exp = now + Env.getSessionTokenExp();
    this.admin = admin;

    JsonObject object = new JsonObject();
    object.addProperty("id", this.admin.getId());
    object.addProperty("email", this.admin.getEmail());
    object.addProperty("username", this.admin.getUsername());
    object.addProperty("name", this.admin.getName());
    object.addProperty("photo_url", this.admin.getPhotoURL());

    this.token = JWT.create().withIssuer(SessionToken.iss).withClaim("sub", this.admin.getId())
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

      this.admin.setId(payload.get("id").getAsString());
      this.admin.setUsername(payload.get("username").getAsString());
      this.admin.setEmail(payload.get("email").getAsString());
      this.admin.setName(payload.get("name").getAsString());
      this.admin.setPhotoURL(payload.get("photo_url").getAsString());

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

  public Admin getAdmin() {
    return admin;
  }

  public String getToken() {
    return token;
  }
}
