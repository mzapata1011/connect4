package com.connectfour;

import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

@WebServlet("/loginServlet")
public class loginServlet extends HttpServlet {

  public void doPost(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException {
    HashMap<String, String> hashmap = new HashMap<String, String>();
    Connection conexion;
    Statement statement;
    String SQL;
    ResultSet rs;
    HttpSession session = request.getSession(true);

    try {
      if (ServletFileUpload.isMultipartContent(request)) {
        hashmap = processMultipartContent(request);
      } else {
        // Handle other content types (if needed)
        response.setContentType("text/plain");
        response.getWriter().write("Unsupported content type");
      }
    } catch (Exception e) {
      e.printStackTrace();
      response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
    } catch (Exception e) {
      System.out.println("Error: " + e);
    }

    String username = hashmap.get("username");
    String password = hashmap.get("password");
    System.out.println("Password = " + password);
    System.out.println("Username = " + username);
    try {
      conexion =
        DriverManager.getConnection(
          "jdbc:mysql://localhost:3306/proyect",
          "root",
          ""
        );
      statement = conexion.createStatement();
      SQL =
        "SELECT user_id FROM users WHERE username = '" +
        username +
        "' AND pwd= '" +
        password +
        "'";
      rs = statement.executeQuery(SQL);

      
      if (!rs.next()) {
        //send response back to the client
        System.out.println("Mal login");
        response.setContentType("text/plain");
        response.getWriter().write("Usuario o contraseña incorrecto"); // ponerle logica a esto cuando se haga la aut
      } else {
        // Login sucessfull
        //#TODO: ver como enviar un auth token al usuario que incluya una timestamp
        System.out.println("Buen login");
        response.getWriter().write("Login Succesful");
        session.setAttribute("username", username);
        String newPageUrl = "index.html";
        response.sendRedirect(newPageUrl);
        
      }
    } catch (Exception e) {
      System.out.println("Error: " + e);
    }
  }

  private HashMap<String, String> processMultipartContent(
    HttpServletRequest request
  ) throws IOException, FileUploadException {
    // Read the multipart content
    DiskFileItemFactory factory = new DiskFileItemFactory();
    ServletFileUpload upload = new ServletFileUpload(factory);
    List<FileItem> items = upload.parseRequest(request);
    HashMap<String, String> hashmap = new HashMap<String, String>();

    for (FileItem item : items) {
      if (item.isFormField()) {
        // Process regular form field
        String fieldName = item.getFieldName();
        String fieldValue = item.getString();
        hashmap.put(fieldName, fieldValue);
      }
    }
    return hashmap;
  }
}
