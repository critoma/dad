<%@ page import = "java.util.*" %>
<HTML>
<HEAD><TITLE>Raspuns la formula card JSP</TITLE></HEAD>

<BODY>
<jsp:useBean id="pp1" class="eu.ase.rmiweb.PictureProcessorBean" />
<%! private String[] rmis = new String[]{"10.2.65.86", "10.2.65.44", "10.2.65.66"}; %>
<%
 
	String numePoza = request.getParameter("nume");
	String newNumePoza = request.getParameter("newNume");
	String xpoz = request.getParameter("xpoz");
	String ypoz = request.getParameter("ypoz");
	String widthstr = request.getParameter("width");
	String heightstr = request.getParameter("height");
	int processingCounts = 25;

	System.setProperty("java.rmi.server.hostname", "10.2.65.95");
	out.println("Web Server IP = " + java.net.InetAddress.getLocalHost());
	out.println("Web Server RMI Server IP = " + System.getProperty("java.rmi.server.hostname"));

	eu.ase.rmiweb.PictureProcessorBean.setRmiServers(rmis);

	Date d1 = new Date();
	
	int res = pp1.processPictureTimeLocal(numePoza, newNumePoza, xpoz, ypoz, widthstr, heightstr, processingCounts);
	//int res = pp1.processPictureTimeRemote(numePoza, newNumePoza, xpoz, ypoz, widthstr, heightstr, processingCounts);

	Date d2 = new Date();
	if (res == 0) {
%>
<H1 ALIGN=CENTER>Raspuns din jsp la cererea din html - Inregistrarea a fost procesata</H1>
<%
	out.println("de la data: " + d1 + " pana la data:" + d2);
      } else {
%>
<H1 ALIGN=CENTER>Raspuns din jsp la cererea din html - Inregistrarea NU a fost procesata </H1>
<%
	}
%>
</BODY></HTML>
