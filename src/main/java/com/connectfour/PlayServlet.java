package com.connectfour;

import java.io.BufferedReader;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/PlayServlet")
public class PlayServlet extends HttpServlet {

  protected void doPost(
    HttpServletRequest request,
    HttpServletResponse response
  ) throws ServletException, IOException {
      //#TODO: conectar a base de datos y actulizar valor con la ficha
      // recibir el jugador que tiene turno para actulizar la bd
        // Read the JSON data from the request body
        BufferedReader reader = request.getReader();
        StringBuilder jsonBody = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            jsonBody.append(line);
        }

        // Process the JSON data as needed
        System.out.println("Received JSON data: " + jsonBody.toString());

        // Send a "Hello, World!" response back to the client
        String responseData = "Hello, World!";
        
        // Set the content type to plain text
        response.setContentType("text/plain");
        
        // Write the response data to the client
        response.getWriter().write(responseData);
    }
  }