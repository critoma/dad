package eu.ase.servletejb;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.ejb.EJB;

import ejbsessionstateless.MyBean;
import ejbsessionstateless.Publisher;

@WebServlet("/HelloServlet4Ejb")
public class HelloServlet4Ejb extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	@EJB
	private MyBean ejbHelloBean;
	
	@EJB
	private Publisher pub;
	
    public HelloServlet4Ejb() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		out.append("Served at: ").append(request.getContextPath());
		out.println("<h1>From here we will call EJB Session Stateless</h1>");
		out.println("<h2>EJB Response = " + ejbHelloBean.doSomething() + "</h2>");
		out.println("<h2>EJB Session Stateless publisher send msg = "+pub.publishNews()+"</h2>");
	}

}
