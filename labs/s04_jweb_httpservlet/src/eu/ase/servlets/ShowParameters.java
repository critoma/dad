package eu.ase.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//File -> New Project ... -> Web - Dynamic Web Project 
//(set Target runtime = Apache Tomcat v9.0: /opt/software/apache-tomcat-9.0.4)
//Project Name = s04_jweb_httpservlet

//File -> New Servlet - Java package = eu.ase.servlets & Class name = ShowParameters
//File -> New HTML File = FormData1.html (HTML5 template)

// Project->right click -> Export...->war file
// Destination directory: /opt/software/java/apache-tomcat-9.0.4/webapps/s04_jweb_httpservlet.war

// or run from Eclipse

// Mozila Firefox Address URL: 
// http://127.0.0.1:8080/s04_jweb_httpservlet/index.html



/**
 * Servlet implementation class ShowParameters
 */
@WebServlet("/ShowParameters")
public class ShowParameters extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowParameters() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String title = "Reading All Request Parameters";

        out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 " +
                                            "Transitional//EN\">\n" +
                    "<HTML>\n" +
                    "<HEAD><TITLE>"+title+"</TITLE></HEAD>\n");


        out.println("<BODY BGCOLOR=\"#FDF5E6\">\n" +
                    "<H1 ALIGN=CENTER>" + title + "</H1>\n" +
                    "<TABLE BORDER=1 ALIGN=CENTER>\n" +
                    "<TR BGCOLOR=\"#FFAD00\">\n" +
                    "<TH>Parameter Name<TH>Parameter Value(s)");

        Enumeration<String> paramNames = request.getParameterNames();

        while(paramNames.hasMoreElements()) {
          String paramName = (String)paramNames.nextElement();
          out.println("<TR><TD>" + paramName + "\n<TD>");
          String[] paramValues = request.getParameterValues(paramName);
          if (paramValues.length == 1) {
            String paramValue = paramValues[0];
            if (paramValue.length() == 0)
              out.print("<I>No Value</I>");
            else
              out.print(paramValue);
          } else {
            out.println("<UL>");
            for(int i=0; i<paramValues.length; i++) {
              out.println("<LI>" + paramValues[i]);
            }
            out.println("</UL>");
          }
        }
        out.println("</TABLE>\n</BODY></HTML>");
      }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
