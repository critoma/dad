package eu.ase.servletejb;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ejbsessionstateless.MyBean;
import ejbsessionstateless.Publisher;

import javax.ejb.*;
import java.io.*;

/**
 * Servlet implementation class HelloEjb
 */
@WebServlet("/HelloEjb")
public class HelloEjb extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	@EJB
	private MyBean ejbHelloBean;
	
	@EJB
	private Publisher ejbPublisher;
	
	public HelloEjb() {
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		out.append("Served at: ").append(request.getContextPath());
		out.println("HelloEJB Servlet!!!");
		out.println("Hello from EJB = " + ejbHelloBean.doSomething());
		out.println("Hello from EJB-MDB: "); 
		ejbPublisher.publishNews();
	
		
	}

}
