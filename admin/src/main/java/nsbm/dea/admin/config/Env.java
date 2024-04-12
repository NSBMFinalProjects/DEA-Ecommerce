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
}
