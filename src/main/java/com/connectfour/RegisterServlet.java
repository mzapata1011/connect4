package com.connectfour;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;


@WebServlet("/registro")
public class RegisterServlet extends HttpServlet {

  public void doPost(HttpServletRequest req, HttpServletResponse res)
    throws IOException, ServletException {

      

    Connection con=null;
    PreparedStatement st=null;
    String SQL,username,password1,password2,email;
    ResultSet rs=null ,rs1=null;
    PrintWriter out= null;
    HttpSession session = req.getSession(true);

   username = req.getParameter("username");
   password1 = req.getParameter("password1");
   password2 =req.getParameter("password2");
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

      SQL =
        "SELECT * FROM users WHERE username =?  OR email =?";
      st=con.prepareStatement(SQL);
      st.setString(1, username);
      st.setString(2, email);
      rs = st.executeQuery();
      out = res.getWriter();
      res.setContentType("text/html");
      out.println("<html><body>");

      
      if(rs.next()){

        // Error registro cuenta existente 
        res.sendRedirect("errorRegistroCuenta.html");       
       
        
      }else{
        password1= PasswordHash.hashPassword(password2);
        SQL = "INSERT INTO users (username,pwd,email) VALUES (?,?,?)";
        st=con.prepareStatement(SQL);
        st.setString(1,username);
        st.setString(2,password1);
        st.setString(3,email);
        st.executeUpdate();
        session.setAttribute("sessionUser",username);
        
        SQL="SELECT user_id FROM users WHERE username = '" +
        username +"'";
        rs1=st.executeQuery(SQL);
        if(rs1.next()){
          session.setAttribute("sessionUser_id", rs.getString(1));
        }
      }
      res.sendRedirect("menu.html");
      out.println("</body></html>");


    } catch (Exception e) {
      System.out.println("Error: " + e);
    } finally {
      try {
        out.close();
        rs.close();
        st.close();
        con.close();
      }catch(SQLException e){
        e.printStackTrace();

      }
    }
  }

 
}
