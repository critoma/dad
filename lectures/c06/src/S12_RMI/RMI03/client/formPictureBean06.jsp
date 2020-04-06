<%@ page import = "java.util.*" %>
<HTML>
<HEAD><TITLE>Raspuns la formula card JSP</TITLE></HEAD>

<BODY>
<jsp:useBean id="pp1" class="eu.ase.rmiweb.PictureProcessorBean" />
<%! private String[] rmis = new String[]{"172.31.45.35", "172.31.33.118", "172.31.45.37"}; %>
<%
 
	String numePoza = request.getParameter("oldName");
	String newNumePoza = request.getParameter("newName");
	String xpoz = request.getParameter("xpos");
	String ypoz = request.getParameter("ypos");
	String widthstr = request.getParameter("width");
	String heightstr = request.getParameter("height");
	String rmiType = request.getParameter("rmiType");
	int processingCounts = 2000;

	//System.setProperty("java.rmi.server.hostname", "10.2.65.95");
	System.setProperty("java.rmi.server.hostname", "172.31.45.35");
	out.println("Web Server IP = " + java.net.InetAddress.getLocalHost());
	out.println("Web Server RMI Server IP = " + System.getProperty("java.rmi.server.hostname"));

	eu.ase.rmiweb.PictureProcessorBean.setRmiServers(rmis);
	int res = 0;
	Date d1 = new Date();
	if(rmiType.startsWith("Local")) {
		int resLocal = pp1.processPictureTimeLocal(numePoza, newNumePoza, xpoz, ypoz, widthstr, heightstr, processingCounts);
		res = resLocal;
	} else {
		int resDistributed = pp1.processPictureTimeRemote(numePoza, newNumePoza, xpoz, ypoz, widthstr, heightstr, processingCounts);
		res = resDistributed;
	}

	Date d2 = new Date();
	if (res == 0) {
%>
<H1 ALIGN=CENTER>Response from JSP to the request from the HTML page : The request has been processed!</H1>
<%
		out.println("From: " + d1 + " , To:" + d2 + "using environment: " + rmiType);
	} else {
%>
<H1 ALIGN=CENTER>Response from JSP to the request from the HTML page : The request has NOT been processed!</H1>
<%
	}
%>
</BODY></HTML>
