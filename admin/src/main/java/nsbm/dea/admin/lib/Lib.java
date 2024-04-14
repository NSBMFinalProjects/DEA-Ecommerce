package nsbm.dea.admin.lib;

import java.io.IOException;
import java.io.PrintWriter;

import com.google.gson.JsonObject;

import jakarta.servlet.http.HttpServletResponse;
import nsbm.dea.admin.enums.Status;

public class Lib {
  public static void sendResponse(HttpServletResponse response, int code, Status status, String message)
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
}
