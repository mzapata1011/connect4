package com.connectfour;

import java.io.*;
import java.sql.*;
import java.util.Arrays;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import org.json.JSONObject;


@WebServlet("/stats")
public class StatsServlet extends HttpServlet {
    
  public void doPost(HttpServletRequest req, HttpServletResponse res)
  throws IOException, ServletException {
    Connection con;
    PreparedStatement ps=null;
    String SQL;
    ResultSet rs;
    // PrintWriter out;
    HttpSession session = req.getSession(true);

    try {
      Class.forName("com.mysql.cj.jdbc.Driver");

      con =
        DriverManager.getConnection(
          "jdbc:mysql://localhost:3306/proyect",
          "root",
          ""
        );

    String player = (String) session.getAttribute("sessionUser_id");
    SQL="SELECT * from partidasTerminadas where user_id=?";
    ps=con.prepareStatement(SQL);
    ps.setString(1, player);
    rs= ps.executeQuery();

    int statsPartidas[]= new int [3];
    int statsPuntos []= new int [3];
    int partidasTotales=0;

    res.setContentType("application/json");
    JSONObject enviar= new JSONObject();
    // out = res.getWriter();
    // out.println("<html><body>");

    while(rs.next()){
        int resultado=(rs.getString(3).equals("w"))?0:(rs.getString(3).equals("l"))?1:2;
        statsPartidas[resultado]++;
        statsPuntos[0]+=rs.getInt("horizontal");
        statsPuntos[1]+=rs.getInt("vertical");
        statsPuntos[2]+=rs.getInt("diagonal");
        partidasTotales++;
        

    }
    int puntosTotales=Arrays.stream(statsPuntos).sum();
    enviar.put("partidas totales", partidasTotales);
    enviar.put("partidas ganadas", statsPartidas[0]);
    enviar.put("partidas perdidas", statsPartidas[1]);
    enviar.put("partidas empatadas", statsPartidas[2]);
    enviar.put("Puntos totales", puntosTotales);
    enviar.put("Puntos horizontales", statsPuntos[0]);
    enviar.put("Puntos verticales", statsPuntos[1]);
    enviar.put("Puntos diagonales", statsPuntos[2]);
    res.getWriter().write(enviar.toString());
    

    // out.println("<Table border=1><tr>");
    // out.println("<th> partidas totales: </th>");
    // out.println("<th> partidas ganadas: </th>");
    // out.println("<th> partidas perdidas: </th>");
    // out.println("<th> partidas empatadas: </th>");
    // out.println("<th> puntos totales: </th>");
    // out.println("<th> partidas horizontales: </th>");
    // out.println("<th> partidas verticales: </th>");
    // out.println("<th> partidas diagonales: </th>");
    // out.println("<th>  puntos por partido: </th>");
    // out.println("</tr>");
    // out.println("<tr><td>"+partidasTotales+"</td>");
    // out.println("<td>"+statsPartidas[0]+" ("+(statsPartidas[0]*100/partidasTotales)+"%)</td>");
    // out.println("<td>"+statsPartidas[1]+" ("+(statsPartidas[1]*100/partidasTotales)+"%)</td>");
    // out.println("<td>"+statsPartidas[2]+" ("+(statsPartidas[2]*100/partidasTotales)+"%)</td>");
    // out.println("<td>"+puntosTotales+"</td>");
    // out.println("<td>"+statsPuntos[0]+" ("+(statsPuntos[0]*100/puntosTotales)+"%)</td>");
    // out.println("<td>"+statsPuntos[1]+" ("+(statsPuntos[1]*100/puntosTotales)+"%)</td>");
    // out.println("<td>"+statsPuntos[2]+" ("+(statsPuntos[2]*100/puntosTotales)+"%)</td>");
    // out.println("<td>"+puntosTotales/partidasTotales+"</td></tr>");
    // out.println("</table>");
    // out.println("</body></html>");

    // out.close();

    if(ps!=null) ps.close();
    if(rs!=null) rs.close();
    if(con!=null) con.close();
}catch(Exception e){
    e.printStackTrace();
    


}



}
}



