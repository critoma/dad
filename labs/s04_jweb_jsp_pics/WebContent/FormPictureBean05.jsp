<html>
<head><title>Raspuns JSP la ajustare poza din HTML</title></head>
<body>
<jsp:useBean id="pp1" class="eu.ase.beans.PictureProcessorBean"></jsp:useBean>
<% 
	String numePoza = request.getParameter("nume");
	String newNumePoza = request.getParameter("newNume");
	String xpoz = request.getParameter("xpoz");
	String ypoz = request.getParameter("ypoz");
	String widthstr = request.getParameter("width");
	String heightstr = request.getParameter("height");
	
	int resp = pp1.processPicture(numePoza, newNumePoza, xpoz, ypoz, widthstr, heightstr);
	if(resp == 0) {
%>
<h1>Raspuns - poza procesata</h1>
<%  } else { %>
<h1>Raspuns - poza NU a fost procesata</h1>
<%  } %>
</body>

</html>