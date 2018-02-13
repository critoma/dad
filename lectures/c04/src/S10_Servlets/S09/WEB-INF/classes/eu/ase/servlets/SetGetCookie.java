package eu.ase.servlets;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;

public class SetGetCookie extends HttpServlet {

  public static final int SECONDS_PER_YEAR = 60*60*24*365;

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	processRequest(request, response);
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	processRequest(request, response);
  }

  public void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType("text/html");
    PrintWriter out = response.getWriter();
    String title = "Set Cookie";
    StringBuffer body = new StringBuffer("<br/>");

    //cookie play
    Cookie[] cookies = request.getCookies();

    //if cookies object is null, it means that no cookie was set before

    if (cookies != null) {
     
     for(int i=0; i<cookies.length; i++) {
      Cookie cookie = cookies[i];
      if ("CookieGigel".equals(cookie.getName())) {
	body.append("<br/><font color=\"red\"> - ").append(cookie.getName()).append(" : ").append(cookie.getValue()).append("</font>");
      } else if ("CookieIon".equals(cookie.getName())) {
        body.append("<br/><font color=\"green\"> - ").append(cookie.getName()).append(" : ").append(cookie.getValue()).append("</font>");
      } else {
	body.append("<br/><font color=\"blue\"> - ").append(cookie.getName()).append(" : ").append(cookie.getValue()).append("</font>");
      }      
     }//end for
     
    } else {
      //create cookie 1 - implicit value in seconds of cookie is within the session
      Cookie userCookie = new Cookie("CookieGigel", "CucuBau");
      response.addCookie(userCookie);

      //create cookie 2 - is per year
      Cookie userCookie2 = new Cookie("CookieIon", "IONIONION");
      userCookie2.setMaxAge(SECONDS_PER_YEAR); 
      response.addCookie(userCookie2);
      
      body.append("<br/><font color=\"red\">").append(" - Cookie: CookieGigel and CookieIon ").append(" added now</font>"); 
    }

    

    out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 " +
                                        "Transitional//EN\">\n" +
                "<HTML>\n" +
                "<HEAD><TITLE>"+title+"</TITLE></HEAD>\n");

    out.println("<BODY BGCOLOR=\"#FDF5E6\">\n" + body + "</BODY></HTML>");


  }
}
