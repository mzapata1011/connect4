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
        //#TODO: tienes que hacer que esto conviert la session en un JSON
        HttpSession session = request.getSession(true);
        JSONObject sesion= new JSONObject();
        String usuario=(String) session.getAttribute("username");
        sesion.put("username", usuario);
        //int numeroPartidas= (Integer) session.getAttribute("numero de Partidas");
        //sesion.put("numero de partidas", numeroPartidas);
        // ArrayList <Integer> partidas= (ArrayList<Integer>) session.getAttribute("partidas");
        // sesion.put("partidas", partidas);

        // for(int i=0; i<numeroPartidas; i++){
        //     sesion.put(String.valueOf(partidas.get(i)),session.getAttribute(String.valueOf(partidas.get(i))));
        // }
        response.setContentType("application/json");
        response.setCharacterEncoding("ASCII");
        response.getWriter().write(sesion.toString());

    }
    
}
