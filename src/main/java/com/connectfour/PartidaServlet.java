package com.connectfour;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.Arrays;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.*;

//#TODO: cambiar por tablero servlet
//#TODO: recibe la informacion del tablero por el valor (int) que recibe del cliente
//#TODO: devuelve el JSON de la  partida adecuada
@WebServlet("/PartidaServlet")
public class PartidaServlet extends HttpServlet {

  protected void doPost(
    HttpServletRequest request,
    HttpServletResponse response
  ) throws ServletException, IOException {
    Connection con;
    ResultSet rs;
    Statement st;
    String SQL;
    PrintWriter out = response.getWriter();
    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
    } catch (Exception e) {
      System.out.println("Error loading JDBC driver: " + e.getMessage());
      return;
    }
    try {
      con =
        DriverManager.getConnection(
          "jdbc:mysql://localhost:3306/proyect",
          "root",
          ""
        );

      HttpSession session = request.getSession(true);
      int numero = (Integer) session.getAttribute("GameId");
      String username = (String) session.getAttribute("sessionUser");
      System.out.println("Estamos en la partida numero: " + numero);
      System.out.println("username= "+username);

      st = con.createStatement();
      

      SQL =
        "SELECT b.number,u3.username AS jugador, u1.username AS jugador_uno, u2.username AS jugador_dos , g.turn,u1.user_id as J1_ID, u2.user_id AS J2_ID " +
        "FROM games g " +
        "LEFT JOIN board b ON g.game_id = b.game " +
        "LEFT JOIN users u1 ON g.player_one = u1.user_id " +
        "LEFT JOIN users u2 ON g.player_two = u2.user_id " +
        "LEFT JOIN users u3 ON b.player = u3.user_id " +
        "WHERE g.game_id =" +
        numero +
        " GROUP BY b.number ";
      rs = st.executeQuery(SQL);

      JSONObject enviar = new JSONObject();
      HashMap<Integer, String> mapa = new HashMap<Integer, String>();
      String j1 = null, j2 = null, turn = null;

      int turnos=0;
      while (rs.next()) {
        if (rs.getString(2) != null) {
          
          String color = rs.getString(2).equals(username) ? "blue" : "red";
          mapa.put(rs.getInt(1), color);
          turnos++;
        }

        if (j1 == null) {
          j1 = rs.getString(3);
          enviar.put("J1", j1);
          enviar.put("J1_ID",rs.getString("J1_ID"));

        }
        if (j2 == null) {
          j2 = rs.getString(4);
          enviar.put("J2", j2);
          enviar.put("J2_ID",rs.getString("J2_ID"));
        }
        if (turn == null) {
          turn = rs.getInt(5) == 1 ? j1 : j2;
        }
      }
      enviar.put("mapa", mapa);
      enviar.put("turno", turn);
      enviar.put("jugador", username);
      enviar.put("partida",numero);

      if (turnos==36){
        Puntos puntos = new Puntos();
        puntos.setMapa(mapa);
        int[][] Puntos = puntos.contadorPuntos();
        int Azules= Arrays.stream(Puntos[0]).sum(), rojos= Arrays.stream(Puntos[1]).sum();

        String ganador= (Azules<rojos)?"victoria":(Azules==rojos)?"empate":"derrota";
        enviar.put("ganador",ganador);
        enviar.put("rojo",rojos);
        enviar.put("azul",Azules);
      }

      // System.out.print("boardMap= ");
      // System.out.println(enviar);
      response.setContentType("application/json");
      response.getWriter().write(enviar.toString());
      System.out.println("enviamos al cliente: "+enviar.toString());

      out.close();
      con.close();
      rs.close();
      st.close();
      
    } catch (JSONException e) {
      e.printStackTrace();
      // Print error message
      System.err.println("Error creating JSONObject: " + e.getMessage());
      // Send appropriate error response if needed
    } catch (Exception e) {
      System.out.println("Error closing resources: " + e.getMessage());
      e.printStackTrace();
    }
  }
}
