package com.connectfour;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/login")
public class loginServlet extends HttpServlet {

  public void doPost(HttpServletRequest req, HttpServletResponse res)
    throws IOException, ServletException {
    Connection con = null;
    Statement st = null;
    String username, password;
    ResultSet rs = null;
    PrintWriter out = null;
    HttpSession session = req.getSession(true);

    username = req.getParameter("username");
    password = req.getParameter("password");

    System.out.println("Password = " + password);
    System.out.println("Username = " + username);
    try {
      con =
        DriverManager.getConnection(
          "jdbc:mysql://127.0.0.1/proyect",
          "root",
          ""
        );
      st = con.createStatement();

      PreparedStatement ps = con.prepareStatement(
        "SELECT * FROM users WHERE username = ? AND pwd = ?"
      );
      ps.setString(1, username);
      ps.setString(2, PasswordHash.hashPassword(password));
      rs = ps.executeQuery();

      out = res.getWriter();
      res.setContentType("text/html");
      out.println("<html><body>");

      if (!rs.next()) {
        //Error en el login
        res.sendRedirect("errorLogin.html");
      } else {
        // Login sucessfull
        session.setAttribute("sessionUser", username);
        session.setAttribute("sessionUser_id", rs.getString(1));
        res.sendRedirect("menu.html");
      }

      out.println("</body></html>");
    } catch (Exception e) {
      System.out.println("Error: " + e);
    } finally {
      try {
        out.close();
        if (st != null) st.close();
        if (rs != null) rs.close();
        if (con != null) con.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }
}
