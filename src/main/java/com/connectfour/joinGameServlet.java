package com.connectfour;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/joinGameServ")
public class joinGameServlet extends HttpServlet {

  public void doGet(HttpServletRequest req, HttpServletResponse res)
    throws IOException, ServletException {
    
    Connection con;
    Statement st,st2;
    String username,SQL,SQL2;
    ResultSet rs,rs2;    
    PrintWriter out;
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

            //Selecciono partidas donde player 2 esta vacio y donde player 1 no sea igual al ID del user
            st2=con.createStatement();
            SQL2 = "SELECT * FROM games WHERE player_two IS NULL AND player_one != "+rs.getInt("user_id");
            rs2=st2.executeQuery(SQL2);

            out = res.getWriter();
            res.setContentType("text/html");
            out.println("<html><body>");

            //Si hay una partida se procede a unirse a la partida y en caso contrario te permite crear una nueva
            if(rs2.next()){
                GameId.setAttribute("GameId", rs2.getInt("game_id"));
                SQL="UPDATE games SET player_two =" + rs.getInt("user_id") + " WHERE game_id =" + GameId.getAttribute("GameId");
                st.executeUpdate(SQL);

                //Redireccionar al tablero/partida
                res.sendRedirect("partida.html");
               
            }else{
               
                out.println("<br><p>No hay partidas a las que unirse, genere una partida ");
                out.println("<a href='newGame.html'>aquí</a></p>");
               
            }

            out.println("</body></html>");
            out.close();

            st.close();
            st2.close();            
            rs.close();
            rs2.close();            
            con.close();



        } catch (Exception e) {
            System.out.println("Error: " + e);
        }

    }
}