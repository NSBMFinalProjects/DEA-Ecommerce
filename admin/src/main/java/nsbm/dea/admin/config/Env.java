package nsbm.dea.admin.config;

import io.github.cdimascio.dotenv.Dotenv;

public class Env {
  private static final Dotenv dotenv = Dotenv.load();

  public static String getDBURL() {
    return dotenv.get("DB_URL");
  }

  public static String getDBUser() {
    return dotenv.get("DB_USER");
  }

  public static String getDBPassword() {
    return dotenv.get("DB_PASSWWORD");
  }

  public static String getRefreshTokenSecret() {
    return dotenv.get("REFRESH_TOKEN_SECRET");
  }

  public static long getRefreshTokenExp() {
    return Long.parseLong(dotenv.get("REFRESH_TOKEN_EXP"));
  }

  public static String getAccessTokenSecret() {
    return dotenv.get("ACCESS_TOKEN_SECRET");
  }

  public static long getAccessTokenExp() {
    return Long.parseLong(dotenv.get("ACCESS_TOKEN_EXP"));
  }

  public static String getSessionTokenSecret() {
    return dotenv.get("SESSION_TOKEN_SECRET");
  }

  public static long getSessionTokenExp() {
    return Long.parseLong(dotenv.get("SESSION_TOKEN_EXP"));
  }

  public static String getRedisURL() {
    return dotenv.get("REDIS_URL");
  }
}
