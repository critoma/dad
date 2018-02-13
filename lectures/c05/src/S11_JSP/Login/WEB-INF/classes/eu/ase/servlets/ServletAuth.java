package eu.ase.servlets;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;

public class ServletAuth extends HttpServlet {
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	processRequest(request, response);
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	processRequest(request, response);
  }

  public void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    //HttpSession session = request.getSession(true);
    response.setContentType("text/html");
    PrintWriter out = response.getWriter();
    String title = "Autentificare";

    out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 " +
                                        "Transitional//EN\">\n" +
                "<HTML>\n" +
                "<HEAD><TITLE>"+title+"</TITLE></HEAD>\n");
  
    out.println("<BODY>\n" +
                "<FORM ACTION=\"auth.jsp\" method =\"POST\">"+
                "<label for = \"user\">User:</label>\n" +
                "<input type=\"text\" name=\"user\">\n\n" +
		"<label for = \"pass\">Pass:</label>\n" +
                "<input type=\"password\" name=\"pass\">\n\n" +
                "<input type=\"submit\" value=\"Trimite\">\n"+
                "</FORM>\n" +
                "</BODY></HTML>");

  }
}
