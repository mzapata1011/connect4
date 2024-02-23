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
    
    Connection con=null;
    PreparedStatement st=null;
    String username,SQL;
    ResultSet rs = null,rs2=null;    
    PrintWriter out=null;
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
            st=con.prepareStatement(SQL);
            st.setString(1, username);
            rs=st.executeQuery();
            rs.next();

            //Selecciono partidas donde player 2 esta vacio y donde player 1 no sea igual al ID del user
            SQL= "SELECT * FROM games WHERE player_two IS NULL AND player_one !=? ";
            st=con.prepareStatement(SQL);
            st.setInt(0, +rs.getInt("user_id"));
            rs2=st.executeQuery();

            out = res.getWriter();
            res.setContentType("text/html");
            out.println("<html><body>");

            //Si hay una partida se procede a unirse a la partida y en caso contrario te permite crear una nueva
            if(rs2.next()){
                GameId.setAttribute("GameId", rs2.getInt("game_id"));
                SQL="UPDATE games SET player_two =?  WHERE game_id = ?" ;
                st= con.prepareStatement(SQL);
                st.setInt(1,rs.getInt("user_id"));
                st.setInt(2,(Integer) GameId.getAttribute("GameId"));
                st.executeUpdate();


                //Redireccionar al tablero/partida
                res.sendRedirect("partida.html");
               
            }else{
               
                out.println("<br><p>No hay partidas a las que unirse, genere una partida ");
                out.println("<a href='newGame.html'>aquí</a></p>");
               
            }

            out.println("</body></html>");




        } catch (Exception e) {
            System.out.println("Error: " + e);
        }finally{
           

            try {
                out.close();
                st.close();
                rs.close();
                rs2.close();            
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }           

        }

    }
}