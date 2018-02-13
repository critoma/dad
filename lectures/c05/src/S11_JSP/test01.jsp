<HTML>
<HEAD>
 <TITLE>Test jsp</TITLE>
</HEAD>
<BODY>
<H2>JSP Expressions</H2>
<UL>
<LI>Current time: <%= new java.util.Date() %> </LI>
<LI>Server: <%= application.getServerInfo() %> </LI>
<LI>Session ID: <%= session.getId() %> </LI>
<LI>The <CODE>testParam</CODE> form parameter: <%= request.getParameter("testParam") %> </LI>
</UL>
</BODY>
</HTML>
