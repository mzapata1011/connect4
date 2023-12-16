package com.connectfour;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.HashMap;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

@WebServlet("/registerServlet")
public class RegisterServlet extends HttpServlet {
  /**
   * #TODO: registro tiene que verificar que:
   * - el username no existe
   * - el correo no esta en uso
   * - las dos contraseñas son iguales
   * - recibir las contraseñas en hash
   */

  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    Connection connection;
    Statement statement;
    String sql;
    HashMap<String, String> userMap = new HashMap<String, String>();
    PrintWriter out;
    // hacer logica de los datos
    try {
      userMap = processMultipartContent(request);
    } catch (FileUploadException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    String usuario = userMap.get("username");
    String email = userMap.get("email");
    String pwd = userMap.get("password");
    // mandar a la base de datos el nuevo user que se crea
    // añadir a bloque condicial de logica de proteccion
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
    } catch (ClassNotFoundException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();

    }
    
    try{
        connection =
        DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/proyect",
            "root",
            ""
        );
        statement = connection.createStatement();
        sql =
        "INSERT INTO users (username,email,pwd)  VALUES ('" +
        usuario +
        "','" +
        email +
        "','" +
        pwd +
        "')";
        System.out.println(sql);
        statement.executeUpdate(sql);
        response.setContentType("text/plain");
        out = response.getWriter();
        out.println("Usuario registrado");
  }catch(Exception e){
    System.err.println(e);
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
        System.out.println(fieldName+" = "+fieldValue);
      }
    }
    return hashmap;
  }
}
