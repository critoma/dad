<%@ page import = "java.util.*" %>
<HTML>
<HEAD><TITLE>Raspuns la formula card JSP</TITLE></HEAD>

<BODY>
<jsp:useBean id="pp1" class="eu.ase.beans.PictureProcessorBean" />
<%
 
	String numePoza = request.getParameter("nume");
	String newNumePoza = request.getParameter("newNume");
	String xpoz = request.getParameter("xpoz");
	String ypoz = request.getParameter("ypoz");
	String widthstr = request.getParameter("width");
	String heightstr = request.getParameter("height");
	int processingCounts = 25;
	Date d1 = new Date();
	//int res = pp1.processPicture(numePoza, newNumePoza, xpoz, ypoz, widthstr, heightstr);
	int res = pp1.processPictureTime(numePoza, newNumePoza, xpoz, ypoz, widthstr, heightstr, processingCounts);
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