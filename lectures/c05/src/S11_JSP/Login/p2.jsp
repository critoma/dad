<HTML>
<HEAD><TITLE>Raspuns la formula card JSP</TITLE></HEAD>

<BODY>
<%
	if(!session.isNew()){
		Integer val=(Integer)session.getAttribute("auth");
		if(val==1){
%>
<jsp:useBean id="fb1" class="eu.ase.beans.FormularBean" />
<%
 
	String nume = request.getParameter("nume");
	String prenume = request.getParameter("prenume");
	String tipcard = request.getParameter("cardType");
	String credit = request.getParameter("credit");
	String cash = request.getParameter("cash");

	int res = fb1.insertInDB(nume, prenume, tipcard, credit, cash);
	if (res == 0) {
%>
<H1 ALIGN=CENTER>Raspuns din jsp la cererea din html - Inregistrarea a fost inserata </H1>
<A HREF="http://127.0.0.1:8080/Login/p1.jsp">BACK</A>
<%
      } else {
%>
<H1 ALIGN=CENTER>Raspuns din jsp la cererea din html - Inregistrarea NU a fost inserata </H1>
<A HREF="http://127.0.0.1:8080/Login/p1.jsp">BACK</A>
<%
	}}}
%>
</BODY></HTML>