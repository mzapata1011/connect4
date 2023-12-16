package com.connectfour;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
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
        
        SQL =
          "SELECT board.game, board.number, board.player FROM board \n" + //
          "LEFT JOIN(games CROSS JOIN users)\n" + //
          "ON (board.game = games.game_id AND games.player_one=users.user_id \n" + //
          "OR board.game=games.game_id AND games.player_two=users.user_id)\n" + //
          "WHERE username='"+username+ "' \n" +
          "ORDER BY board.game, board.number;";  //Conectar usuario al nombre de usuario del jugador
        rs = statement.executeQuery(SQL);

        HashMap<String, String> boardMap = new HashMap<String, String>();
        ArrayList<Integer> partidas = new ArrayList<Integer>();
        int numeroPartidas = 0;
        
        String color;
        while (rs.next()) {
          if (partidas.isEmpty()) {
            numeroPartidas++;
            partidas.add(rs.getInt(1));
          } else if (partidas.get(numeroPartidas - 1) != rs.getInt(1)) {
            usuario.put(
              String.valueOf(partidas.get(numeroPartidas - 1)),
              boardMap
            );
            session.setAttribute(String.valueOf(partidas.get(numeroPartidas-1)), new JSONObject(boardMap));
            boardMap.clear();
            
            numeroPartidas++;
            partidas.add(rs.getInt(1));
          }
          color = (rs.getInt(3) == 1) ? "blue" : "red";
          boardMap.put(rs.getString(2), color);
        }

        usuario.put(String.valueOf(partidas.get(numeroPartidas - 1)), boardMap);
        session.setAttribute(String.valueOf(partidas.get(numeroPartidas-1)), new JSONObject(boardMap));
        boardMap.clear();
        usuario.put("partidas", partidas);
        session.setAttribute("numero de Partidas", numeroPartidas);
        session.setAttribute("partidas", partidas);
        usuario.put("numero de partidas",numeroPartidas);
      } catch (Exception e) {

        System.out.println(e);
      }
    }
    usuario.put("username", username);
    
    response.setContentType("application/json");
    response.setCharacterEncoding("ASCII");


    response.getWriter().write(usuario.toString());
    // System.out.println("username= " + username);
    // // Convert session data to JSON
    // String jsonData = "{ \"username\": \"" + username + "\" }";

    // // Set content type to JSON
    // response.setContentType("application/json");
    // response.setCharacterEncoding("UTF-8");

    // // Write JSON data to response
    // response.getWriter().write(jsonData);
  }
}
