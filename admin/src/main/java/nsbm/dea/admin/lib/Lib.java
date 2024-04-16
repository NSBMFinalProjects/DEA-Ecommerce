package nsbm.dea.admin.lib;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import nsbm.dea.admin.enums.Status;

public class Lib {
  public static final Gson gson = new Gson();

  public static void sendJSONResponse(HttpServletResponse response, int code, Status status, String message)
      throws IOException {
    response.setContentType("application/json");
    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    PrintWriter out = response.getWriter();

    JsonObject object = new JsonObject();
    object.addProperty("status", status.toString());
    object.addProperty("message", message);
    out.println(object.toString());
    return;
  }

  public static JsonObject getJSONPayloadFromRequest(HttpServletRequest request) throws IOException {
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()))) {
      StringBuilder jsonStr = new StringBuilder();
      String line;
      while ((line = reader.readLine()) != null) {
        jsonStr.append(line);
      }

      return gson.fromJson(jsonStr.toString(), JsonObject.class);
    }
  }
}
