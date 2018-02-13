<HTML>
<HEAD>
<TITLE>Using JavaServer Pages</TITLE>

<META NAME="keywords" CONTENT="JSP,JavaServer Pages,servlets">
<META NAME="description" CONTENT="A quick example of the four main JSP tags.">
</HEAD>

<BODY BGCOLOR="#FDF5E6" TEXT="#000000" LINK="#0000EE" VLINK="#551A8B" ALINK="#FF0000">

<CENTER>
<TABLE BORDER=5 BGCOLOR="#EF8429">
  <TR><TH CLASS="TITLE">
      JSP Directive si scriptlets</TABLE>
</CENTER>
<P>

Some dynamic content created using various JSP mechanisms:
<UL>
  <LI><B>Expression.</B><BR/>
  Your hostname: <%= request.getRemoteHost() %>.
  </LI>
  <LI><B>Scriptlet.</B><BR/>
  <% out.println("Attached GET data: " + request.getQueryString()); %>
  </LI>
  <LI><B>Declaration (plus expression).</B><BR/>
      <%! private int accessCount = 0; %>
      Accesses to page since server reboot: <%= ++accessCount %>
  </LI>
  <LI><B>Directive (plus expression).</B><BR/>
      <%@ page import = "java.util.*" %>
      Current date: <%= new Date() %>
  </LI>
  <LI><B>Directive (include).</B><BR/>
      <%@ include file="/testInclude02.html" %>
  </LI>
</UL>

</BODY>
</HTML>