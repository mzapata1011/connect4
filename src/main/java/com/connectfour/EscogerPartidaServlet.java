package com.connectfour;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

/**
 * We create a HTML based on the started games made of the user
 */
@WebServlet("/startedGames")
public class EscogerPartidaServlet extends HttpServlet {


  public void doGet(HttpServletRequest req, HttpServletResponse res)
    throws IOException, ServletException {
    
    Connection con;
    // Statement st,st2,st3;
    PreparedStatement st=null,st2=null,st3=null;
    String username,SQL;
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
      // st = con.createStatement();
      // st2 = con.createStatement();

      // User table based on id
      SQL ="SELECT * FROM users WHERE username = ?";
      st=con.prepareStatement(SQL);
      st.setString(1,username);
      rs=st.executeQuery(SQL);
      rs.next();
      idUser = rs.getInt("user_id");

      //Active games of the user
      SQL="SELECT * FROM games WHERE (player_one = ? AND player_two IS NOT NULL AND active = 1) OR (player_two = ?  AND player_one IS NOT NULL AND active = 1)";  
      st2=con.prepareStatement(SQL);    
      st2.setInt(1, idUser);  
      st2.setInt(2, idUser);  
      rs2 = st2.executeQuery();


      //html code
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


      //HTML depending on the user
      if (rs2.next()) {

        out.println("<header><h1>Conect4</h1></header>");
        out.println("<br><div>");
        out.println("<a>Seleccione una partida</a>");

        //List of games
        do{

          
          //Obtengo el username del player 1
          SQL = "SELECT * FROM users WHERE user_id =?" ;
          st3 = con.prepareStatement(SQL);
          st3.setInt(1,rs2.getInt("player_one"));
          rs3 = st3.executeQuery();
          rs3.next();
          String player1 = rs3.getString("username");

          // player 2
          SQL = "SELECT * FROM users WHERE user_id =?" ;
          st3 = con.prepareStatement(SQL);
          st3.setInt(1,rs2.getInt("player_two"));
          rs3 = st3.executeQuery();
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
        //No active games
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
