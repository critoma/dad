package eu.ase.servlets;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class SetResponseHeader extends HttpServlet {
  private int accCount = 0;
  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {
    accCount++;
    response.setHeader("Refresh", "3");
    response.setContentType("text/html");
    PrintWriter out = response.getWriter();
    out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 " +
                                        "Transitional//EN\">\n" +
                "<HTML>\n" +
                "<HEAD><TITLE>Hello WWW</TITLE></HEAD>\n" +
                "<BODY>\n" +
                "<H1>Hello WWW</H1>\n<P>" + accCount + 
                "</P></BODY></HTML>");
  }
}
