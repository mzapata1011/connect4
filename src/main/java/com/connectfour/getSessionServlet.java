package com.connectfour;


import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

@WebServlet("/getSessionServlet")
public class getSessionServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        
        HttpSession session = request.getSession(true);
        JSONObject sesion= new JSONObject();
        String usuario=(String) session.getAttribute("username");
        sesion.put("username", usuario);

        response.setContentType("application/json");
        response.setCharacterEncoding("ASCII");
        response.getWriter().write(sesion.toString());

    }
    
}
