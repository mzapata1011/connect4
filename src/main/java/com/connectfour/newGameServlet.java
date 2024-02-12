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
    
    Connection con;
    Statement st;
    String username,SQL;
    ResultSet rs;    
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

            //Miro la info del User
            st = con.createStatement();
            SQL ="SELECT * FROM users WHERE username ='"+ username +"'";
            rs=st.executeQuery(SQL);
            rs.next();
            
            //Introduzco en Player1 el ID del user y se genera una nueva ID en games
            SQL = "INSERT INTO games (player_one) VALUES ('"+ rs.getInt("user_id")+"')" ;
            st.executeUpdate(SQL);

            //Selecciono el ID del game
            SQL = "SELECT game_id FROM games ORDER BY game_id DESC LIMIT 1";
            rs = st.executeQuery(SQL);
            rs.next();
            GameId.setAttribute("GameId", rs.getInt("game_id"));

            //Redireccion al tablero de la partida
            res.sendRedirect("resume");

            rs.close();
            st.close();
            con.close();


        } catch (Exception e) {
            System.out.println("Error: " + e);
        }

    }
}