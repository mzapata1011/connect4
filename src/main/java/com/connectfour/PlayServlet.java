package com.connectfour;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.*;
import java.util.Arrays;
import java.util.HashMap;
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
    PreparedStatement statsStatement=null;
    ResultSet rs=null;
    Statement st=null;
    BufferedReader reader = request.getReader();
    StringBuilder jsonBody = new StringBuilder();
    String line,SQL;


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
      System.out.println("Game_id= "+game);
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

      // String responseData =
      //   "El jugador " + player + " ha jugado en la casilla " + number;

      // Cambio de turno
      String updateQuery = "UPDATE games SET turn = NOT turn WHERE game_id = ?";
      psUpdate = con.prepareStatement(updateQuery);
      psUpdate.setInt(1, game);
      psUpdate.executeUpdate();

      //si la partida esta terminada vemos el mapa, los puntos
      if (jsonObject.getInt("turnos") == 36) {

        String ganadorQuery =
        "UPDATE games SET active = ?  WHERE game_id = ?";
        psUpdate = con.prepareStatement(ganadorQuery);
        psUpdate.setBoolean(1, false); // Set the new value for the active column
        psUpdate.setInt(2, game); // Set the value for the game_id column
        @SuppressWarnings("unused")
        int ex= psUpdate.executeUpdate();

        HashMap<Integer, String> mapa = new HashMap<Integer, String>();
        SQL="SELECT number,player FROM board WHERE game="+game;
        st=con.createStatement();
        rs=st.executeQuery(SQL);
        while (rs.next()) { 
            String color = rs.getString(2).equals(player) ? "blue" : "red";
            mapa.put(rs.getInt(1), color);
          }
          
        // JSONObject Jsonmapa = jsonObject.getJSONObject("mapa");
        System.out.println(mapa);
        // Iterator<String> keys = Jsonmapa.keys();

        // //pasamos el mapa a un hashmap
        // while (keys.hasNext()) {
        //   String key = keys.next();
        //   String value = Jsonmapa.getString(key);
        //   mapa.put(Integer.parseInt(key), value);


        // }// fin while has keys

        System.out.print("mapa= " + mapa);
        Puntos puntos = new Puntos();
        puntos.setMapa(mapa);
        int[][] Puntos = puntos.contadorPuntos();
        int Azules= Arrays.stream(Puntos[0]).sum(), rojos= Arrays.stream(Puntos[1]).sum();
        System.out.println("Azules tiene: " + Azules + " puntos");
        System.out.print("Rojo tiene: " + rojos + " puntos");



        // responseData +=
        //   " Azules tiene: " +
        //   Azules +
        //   " puntos, Rojo tiene: " +
        //   rojos +
        //   " puntos";

          //añadimos los resultados a la tabla de partidas terminadas
          String statsQuery="INSERT into partidasTerminadas (game_id,user_id,result,horizontal,vertical,diagonal)"+ 
                            "VALUES (?,?,?,?,?,?),(?,?,?,?,?,?)";
          statsStatement = con.prepareStatement(statsQuery);

          //valores del jugador 1
          statsStatement.setInt(1,game);
          statsStatement.setInt(2,jsonObject.getInt("J1_id"));
          statsStatement.setString(3,(Azules<rojos)?"w":(Azules==rojos)?"t":"l");
          statsStatement.setInt(4,Puntos[1][0]);
          statsStatement.setInt(5,Puntos[1][1]);
          statsStatement.setInt(6,Puntos[1][2]);
          //valores del jugador 2
          statsStatement.setInt(7,game);
          statsStatement.setInt(8,jsonObject.getInt("J2_id"));
          statsStatement.setString(9,(Azules>rojos)?"w":(Azules==rojos)?"t":"l");
          statsStatement.setInt(10,Puntos[0][0]);
          statsStatement.setInt(11,Puntos[0][1]);
          statsStatement.setInt(12,Puntos[0][2]);

          @SuppressWarnings("unused")
          int rowsAffected = statsStatement.executeUpdate();
        jsonObject.remove("number");
        jsonObject.remove("mapa");
        jsonObject.remove("J2_id");
        jsonObject.remove("turnos");
        jsonObject.put("azules",Azules);
        jsonObject.put("rojos",rojos);
        
        // RefreshSocket.refreshActiveGame(game+"");

      }//fin del if turno==36
        
      
      // Set the content type to plain text
      response.setContentType("application/json");

      // Write the response data to the client
      response.getWriter().write(jsonObject.toString());

    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      // Close resources
      try {
        if (psInsert != null) psInsert.close();
        if (psUpdate != null) psUpdate.close();
        if(statsStatement !=null) statsStatement.close();
        if(st != null) st.close();
        if (con != null) con.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }
}


