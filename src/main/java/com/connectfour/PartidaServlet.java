package com.connectfour;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.*;
 //#TODO: cambiar por tablero servlet
 //#TODO: recibe la informacion del tablero por el valor (int) que recibe del cliente
 //#TODO: devuelve el JSON de la session
@WebServlet("/PartidaServlet")
public class PartidaServlet extends HttpServlet {

  protected void doGet(
    HttpServletRequest request,
    HttpServletResponse response
  ) throws ServletException, IOException {
    Connection con;
    ResultSet rs;
    Statement st;
    String SQL;
    
    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
    } catch (Exception e) {
      System.out.println("Error loading JDBC driver: " + e.getMessage());
      return;
    }
    try {
      response.setContentType("application/json");
      PrintWriter out = response.getWriter();
      con =
        DriverManager.getConnection(
          "jdbc:mysql://localhost:3306/proyect",
          "root",
          ""
        );
      st = con.createStatement();
      int numero=0; // numero tiene que tener e valor del boton que se usa en index
      SQL = "SELECT * FROM board WHERE game="+ numero +"ORDER BY number"; //#TODO: numero tiene que estaar conectado a la pagina de index
      rs = st.executeQuery(SQL);
      HashMap<String, String> boardMap = new HashMap<String, String>();
      while (rs.next()) {
        String color;
        if (rs.getString(3).equals("1")) color = "blue"; else color = "red";
        boardMap.put(rs.getString(2), color); //#TODO: convertir a JSON y mandar a html
      }
      JSONObject enviar = new JSONObject(boardMap);
      out.print(enviar);
      out.flush();

      con.close();
      rs.close();
      st.close();
    } catch (Exception e) {
      System.out.println("Error closing resources: " + e.getMessage());
    }
  }
}
