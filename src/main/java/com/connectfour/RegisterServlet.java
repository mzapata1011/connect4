package com.connectfour;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.security.MessageDigest;

@WebServlet("/registro")
public class RegisterServlet extends HttpServlet {

  public void doPost(HttpServletRequest req, HttpServletResponse res)
    throws IOException, ServletException {

      

    Connection con;
    Statement st;
    String SQL,username,password1,password2,email;
    ResultSet rs;
    PrintWriter out;
    HttpSession session = req.getSession(true);

   username = req.getParameter("username");
   password1 = req.getParameter("password");
   password2 =req.getParameter("password");
   email = req.getParameter("email");

   if(!password1.equals(password2)) res.sendRedirect("errorRegistroCuenta.html");
    //Comprobar datos
    System.out.println("Password = " + password1);
    System.out.println("Username = " + username);
    System.out.println("Email = " + email);

    try {
      con =
        DriverManager.getConnection(
          "jdbc:mysql://127.0.0.1/proyect",
          "root",
          ""
        );
      st = con.createStatement();

      SQL =
        "SELECT * FROM users WHERE username = '" +
        username +       
        "' OR email = '" +
        email + "'";
        
      rs = st.executeQuery(SQL);
      out = res.getWriter();
      res.setContentType("text/html");
      out.println("<html><body>");

      
      if(rs.next()){

        // Error registro cuenta existente 
        res.sendRedirect("errorRegistroCuenta.html");       
       
        
      }else{
        password1= PasswordHash.hashPassword(password2);
        SQL = "INSERT INTO users (username,pwd,email) VALUES ('"+ username +"','"+ password1 +"' , '" + email +"')";
        st.executeUpdate(SQL);
        session.setAttribute("sessionUser",username);
        res.sendRedirect("menu.html");
      }

      out.println("</body></html>");
      out.close();
      rs.close();
      st.close();
      con.close();

    } catch (Exception e) {
      System.out.println("Error: " + e);
    }
  }

 
}
