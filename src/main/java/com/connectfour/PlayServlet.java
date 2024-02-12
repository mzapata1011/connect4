package com.connectfour;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONObject;

@WebServlet("/PlayServlet")
public class PlayServlet extends HttpServlet {

  protected void doPost(    HttpServletRequest request,
    HttpServletResponse response
  ) throws ServletException, IOException {
    // recibir el jugador que tiene turno para actulizar la bd
    Connection con = null;
    PreparedStatement psInsert = null;
    PreparedStatement psUpdate = null;
    BufferedReader reader = request.getReader();
    StringBuilder jsonBody = new StringBuilder();
    String line;

    try {
      while ((line = reader.readLine()) != null) {
        jsonBody.append(line);
      }

      // Process the JSON data as needed
      System.out.println("Received JSON data: " + jsonBody.toString());
      JSONObject jsonObject = new JSONObject(jsonBody.toString());

      Class.forName("com.mysql.cj.jdbc.Driver");

      con =
        DriverManager.getConnection(
          "jdbc:mysql://localhost:3306/proyect",
          "root",
          ""
        );

      HttpSession session = request.getSession(true);
      Integer game = (Integer) session.getAttribute("GameId");
      String number = jsonObject.getString("number");
      String player = (String) session.getAttribute("sessionUser_id");

      //Añado la ficha a la base de datos
      String insertaJugada =
        "INSERT INTO board (game, number, player) VALUES (?, ?, ?)";
      psInsert = con.prepareStatement(insertaJugada);
      psInsert.setInt(1, game);
      psInsert.setString(2, number);
      psInsert.setString(3, player);
      psInsert.executeUpdate();

      String responseData =
        "El jugador " + player + " ha jugado en la casilla " + number;

      // Cambio de turno
      String updateQuery = "UPDATE games SET turn = NOT turn WHERE game_id = ?";
      psUpdate = con.prepareStatement(updateQuery);
      psUpdate.setInt(1, game);
      psUpdate.executeUpdate();

      if (jsonObject.getInt("turnos") == 36) {
        HashMap<Integer, String> mapa = new HashMap<Integer, String>();
        JSONObject Jsonmapa = jsonObject.getJSONObject("mapa");
        System.out.println(Jsonmapa);
        Iterator<String> keys = Jsonmapa.keys();
        while (keys.hasNext()) {
          String key = keys.next();
          String value = Jsonmapa.getString(key);
          mapa.put(Integer.parseInt(key), value);
        }
        System.out.print("mapa= " + mapa);
        Puntos puntos = new Puntos();
        puntos.setMapa(mapa);
        int[][] Puntos = puntos.contadorPuntos();
        int Azules= Arrays.stream(Puntos[1]).sum(), rojos= Arrays.stream(Puntos[0]).sum();;
        System.out.println("Azules tiene: " + Azules + " puntos");
        System.out.print("Rojo tiene: " + rojos + " puntos");
        int ganador = (rojos > Azules) ? 0 : 1;

        String ganadorQuery =
          "UPDATE games SET active = ?, turn = ?  WHERE game_id = ?";
        psUpdate = con.prepareStatement(ganadorQuery);
        psUpdate.setInt(1, ganador); // Set the value for the turn column
        psUpdate.setBoolean(2, false); // Set the new value for the active column
        psUpdate.setInt(3, game); // Set the value for the game_id column
        psUpdate.executeUpdate();

        responseData +=
          " Azules tiene: " +
          Azules +
          " puntos, Rojo tiene: " +
          rojos +
          " puntos";

          //#TODO: hacer la nueva tabla de partidas terminadas y poner las estasdisticas de la partida
      }
      // Set the content type to plain text
      response.setContentType("text/plain");

      // Write the response data to the client
      response.getWriter().write(responseData);
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      // Close resources
      try {
        if (psInsert != null) psInsert.close();
        if (psUpdate != null) psUpdate.close();
        if (con != null) con.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }
}
