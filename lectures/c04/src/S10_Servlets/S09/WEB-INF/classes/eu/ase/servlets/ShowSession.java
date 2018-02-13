package eu.ase.servlets;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;

public class ShowSession extends HttpServlet {
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	processRequest(request, response);
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	processRequest(request, response);
  }

  public void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    HttpSession session = request.getSession(true);
    response.setContentType("text/html");
    PrintWriter out = response.getWriter();
    String title = "Show Session";
    String heading;
    Integer accessCount = new Integer(0);;
    if (session.isNew()) {
      heading = "Welcome, Newcomer";
      session.setAttribute("accessCount", accessCount);
    } else {
      heading = "Welcome Back";
      //Integer oldAccessCount =(Integer)session.getValue("accessCount"); 
	Integer oldAccessCount =new Integer(""+session.getAttribute("accessCount"));
        // Use getAttribute, not getValue, in version
        // 2.2 of servlet API.
        //(Integer)session.getValue("accessCount"); 
        //(Integer)session.getAttribute("accessCount");
      if (oldAccessCount != null) {
        accessCount = new Integer(oldAccessCount.intValue() + 1);
      }
    }
    // Use setAttribute in version 2.2 of servlet API.
    //session.putValue("accessCount", accessCount);
    session.setAttribute("accessCount", ""+accessCount); 
    
    
    out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 " +
                                        "Transitional//EN\">\n" +
                "<HTML>\n" +
                "<HEAD><TITLE>"+title+"</TITLE></HEAD>\n");
  
    out.println("<BODY BGCOLOR=\"#FDF5E6\">\n" +
                "<H1 ALIGN=\"CENTER\">" + heading + "</H1>\n" +
                "<H2>Information on Your Session:</H2>\n" +
                "<TABLE BORDER=1 ALIGN=CENTER>\n" +
                "<TR BGCOLOR=\"#FFAD00\">\n" +
                "  <TH>Info Type<TH>Value\n" +
                "<TR>\n" +
                "  <TD>ID\n" +
                "  <TD>" + session.getId() + "\n" +
                "<TR>\n" +
                "  <TD>Creation Time\n" +
                "  <TD>" + new Date(session.getCreationTime()) + "\n" +
                "<TR>\n" +
                "  <TD>Time of Last Access\n" +
                "  <TD>" + new Date(session.getLastAccessedTime()) + "\n" +
                "<TR>\n" +
                "  <TD>Number of Previous Accesses\n" +
                "  <TD>" + accessCount + "\n" +
                "</TABLE>\n" +
                "</BODY></HTML>");
  }
}
