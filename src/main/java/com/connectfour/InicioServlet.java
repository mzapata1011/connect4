package com.connectfour;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONObject;

@WebServlet("/InicioServlet")
public class InicioServlet extends HttpServlet {

  public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
    Connection conexion;
    Statement statement;
    String SQL;
    ResultSet rs;
    JSONObject usuario = new JSONObject();
    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
    } catch (Exception e) {
      System.out.println("Error: " + e);
    }

    HttpSession session = request.getSession(true);

    String username = (String) session.getAttribute("username");
    System.out.println("username= "+username);
    if (username == null) {
      session.setAttribute("username", "guest");
      username = (String) session.getAttribute("username");
    } else {
      try {
        conexion =
          DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/proyect",
            "root",
            ""
          );
        statement = conexion.createStatement();
        
        SQL = "SELECT game_id, player_one, player_two, turn " +
        "FROM games " +
        "INNER JOIN users ON (player_one = user_id) OR (player_two = user_id) " +
        "WHERE username = '" + username + "'";

        rs = statement.executeQuery(SQL);
        ArrayList<Integer> partidas = new ArrayList<Integer>();
        ArrayList<Boolean> turnos = new ArrayList<Boolean>();
        

        while(rs.next()){
          partidas.add(rs.getInt(1));
          boolean condition = (rs.getString(2).equals("1")) ? (rs.getInt(4) == 1) : (rs.getInt(4) == 0);
          turnos.add(condition);
        }
        System.out.print("partidas: ");
        System.out.println(partidas);
        System.out.print("turnos: ");
        System.out.println(turnos);
        
        usuario.put("partidas",partidas);
        usuario.put("turno",turnos);

      } catch (Exception e) {

        System.out.println(e);
      }
    }
    usuario.put("username", username);
    System.out.print("USUARIO: ");
    System.out.println(usuario);
    response.setContentType("application/json");
    response.setCharacterEncoding("ASCII");
    response.getWriter().write(usuario.toString());
  }
}
