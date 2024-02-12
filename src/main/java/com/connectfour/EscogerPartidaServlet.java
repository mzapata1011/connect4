package com.connectfour;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/startedGames")
public class EscogerPartidaServlet extends HttpServlet {

  public void doGet(HttpServletRequest req, HttpServletResponse res)
    throws IOException, ServletException {
    
    Connection con;
    Statement st,st2,st3;
    String username,SQL,SQL2,SQL3;
    ResultSet rs,rs2,rs3;
    PrintWriter out;
    HttpSession session = req.getSession(false);
    int idUser;
    
    username = (String) session.getAttribute("sessionUser");
   

    
    try {
      con =
        DriverManager.getConnection(
          "jdbc:mysql://127.0.0.1/proyect",
          "root",
          ""
        );
      st = con.createStatement();
      st2 = con.createStatement();

      //Primero vemos la tabla de usuarios para obtener el ID
      SQL ="SELECT * FROM users WHERE username = '" + username +"'";
      rs=st.executeQuery(SQL);
      rs.next();
      idUser = rs.getInt("user_id");

      //Segundo comprobamos las partidas activas del usuario
      SQL2="SELECT * FROM games WHERE (player_one = "+ idUser +" AND player_two IS NOT NULL AND active = 1) OR (player_two = "+ idUser + " AND player_one IS NOT NULL AND active = 1)";        
      rs2 = st2.executeQuery(SQL2);

      //Empezamos el codigo para el html
      out = res.getWriter();
      res.setContentType("text/html");
      out.println("<!DOCTYPE html>");
      out.println("<html lang='en'>");
      out.println("<head>");
      out.println("<meta charset='UTF-8'>");
      out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
      out.println("<link rel='icon' href='../resources/favicon.ico.png' type='iamge/x-icon'>");
      out.println("<title>Conect4</title></head>");
      out.println("<body>");

      //Resto del html se irá creando según los resultados obtenidos en las consultas SQL
      if (rs2.next()) {

        out.println("<header><h1>Conect4</h1></header>");
        out.println("<br><div>");
        out.println("<a>Seleccione una partida</a>");

        //Generacion listado partidas
        do{

          st3 = con.createStatement();
          //Obtengo el username del player 1
          SQL3 = "SELECT * FROM users WHERE user_id =" +rs2.getInt("player_one");
          rs3 = st3.executeQuery(SQL3);
          rs3.next();
          String player1 = rs3.getString("username");

          //Obtengo el username del player 2
          SQL3 = "SELECT * FROM users WHERE user_id =" +rs2.getInt("player_two");
          rs3 = st3.executeQuery(SQL3);
          rs3.next();
          String player2 = rs3.getString("username");

          out.println("<form action='resume' method=POST>");
          out.println("<a>"+ player1 +" VS </a><a>"+ player2 +"</a>");
          out.println("<input type=hidden value="+ rs2.getInt("game_id") +" name = idResume>");
          out.println("<input type='submit' value= 'resume game' >");
          out.println("</form>");

        }while(rs2.next());
        out.println("<br><a href='play.html'>");
        out.println("<input value='Volver'>");
        out.println("</a></div>");

      } else {
        //Caso donde no tienes partidas activas
        out.println("<div>");
        out.println("<h1>No tienes partidas activas en este momento.</h1>");
        out.println("<br><p>Para crear una nueva partida pulse ");
        out.println("<a href='newGame'>aquí</a></p>");
        out.println("<br><a href='play.html'><input value='Volver'></a></div>");
        
      }

      out.println("</body></html>");
      out.close();
      rs.close();
      rs2.close();
      st.close();
      st.close();
      con.close();

    } catch (Exception e) {
      System.out.println("Error: " + e);
    }
  }

 
}
