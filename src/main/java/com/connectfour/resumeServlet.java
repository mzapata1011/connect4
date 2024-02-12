package com.connectfour;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/resume")
public class resumeServlet extends HttpServlet {

    public void doPost (HttpServletRequest req, HttpServletResponse res)
     throws IOException, ServletException{

        HttpSession GameId =req.getSession(true);
        PrintWriter out;
        String ResumeId;
         System.out.println("estamos en resume ya te mando a la partida");
        ResumeId = req.getParameter("idResume");
        out = res.getWriter();
        res.setContentType("text/html");
        GameId.setAttribute("GameId", Integer.parseInt(ResumeId));   
        System.out.println("Quiero ir a la partida: "+ResumeId);

        //Redireccionar al tablero para jugar la partida   
        res.sendRedirect("partida.html");
   
        out.close();
        
     }
    
}
