package com.connectfour;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/newGameServ")
public class newGameServlet extends HttpServlet {

  public void doGet(HttpServletRequest req, HttpServletResponse res)
    throws IOException, ServletException {
    
    Connection con=null;
    PreparedStatement st=null;
    String username,SQL;
    ResultSet rs=null;    
    HttpSession session;
    HttpSession GameId = req.getSession(true);
    
    

    session = req.getSession(false);
    username = (String)session.getAttribute("sessionUser");

    try {
            con =
             DriverManager.getConnection(
              "jdbc:mysql://127.0.0.1/proyect",
              "root",
              ""
            );


            SQL ="SELECT * FROM users WHERE username =?";
            st = con.prepareStatement(SQL);
            st.setString(1,username);
            rs=st.executeQuery(SQL);
            rs.next();
            
            SQL = "INSERT INTO games (player_one) VALUES (?)" ;
            st=con.prepareStatement(SQL);
            st.setInt(1,rs.getInt("user_id"));
            st.executeUpdate();

            //Selecciono el ID del game
            SQL = "SELECT game_id FROM games ORDER BY game_id DESC LIMIT 1";
            st=con.prepareStatement(SQL);
            rs = st.executeQuery();
            rs.next();
            GameId.setAttribute("GameId", rs.getInt("game_id"));

            //Redireccion al tablero de la partida
            res.sendRedirect("partida.html");



        } catch (Exception e) {
            System.out.println("Error: " + e);
        } finally{
        
          try {
            rs.close();
            con.close();
            st.close();
          } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }
          
        }

    }
}