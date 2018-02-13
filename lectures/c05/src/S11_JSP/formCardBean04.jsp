<HTML>
<HEAD><TITLE>Raspuns la formula card JSP</TITLE></HEAD>

<BODY>
<jsp:useBean id="fb1" class="eu.ase.beans.FormularBean" />
<%
 
	String nume = request.getParameter("nume1");
	String prenume = request.getParameter("nume2");
	String tipcard = request.getParameter("cardType");

	int res = fb1.insertInDB(nume, prenume, tipcard);
	if (res == 0) {
%>
<H1 ALIGN=CENTER>Raspuns din jsp la cererea din html - Inregistrarea a fost inserata </H1>
<%
      } else {
%>
<H1 ALIGN=CENTER>Raspuns din jsp la cererea din html - Inregistrarea NU a fost inserata </H1>
<%
	}
%>
</BODY></HTML>