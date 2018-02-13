<%@ page import="java.util.*" %>
<%@ taglib uri="/testTaglib" prefix="test" %>

<html>
<head>
  <title>Testing Tag Library</title>
</head>
<body>

<test:tagError>Invalid user name</test:tagError>
<test:tagError color="blue">Invalid Password</test:tagError>

<%
List items = new ArrayList();
items.add("AAA");
items.add("BBB");
items.add("CCC");
%>
<test:tagList items='<%=items%>'>

</test:tagList>
<test:tagList>
one
two
three
</test:tagList>

</body>
</html>