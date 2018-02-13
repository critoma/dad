<!DOCtype HTML PUBLIC "-//W3C//DTD HTML 4.01 Frameset//EN"
   "http://www.w3.org/TR/html4/frameset.dtd">
<HTML>
<HEAD>
<TITLE>FORM</TITLE>
</HEAD>

<BODY>
<%
	if(session!=null){
		Integer val=(Integer)session.getAttribute("auth");
		//atentie:la configurarea browserului-all cookies blocked
		if(val==1){
%>

<FORM action="p2.jsp" method="POST">
  <p>Formular</p>
 <label for="nume">Nume:</label>
	<input type="text" name="nume"><br/><br/>
 <label for="prenume">Prenume:</label>
	<input type="text" name="prenume"><br/><br/>
 <label for="cardtype">Card:</label><br/>
 <input type="radio" name="cardtype" value="Visa">Visa<br/>
 <input type="radio" name="cardtype" value="Master Card">Master Card<br/>
 <input type="radio" name="cardtype" value="Amex">American Express<br/>
 <input type="radio" name="cardtype" value="Discover">Discover<br/>
 <input type="radio" name="cardtype" value="Java SmartCard">Java SmartCard<br/><br/>

 <label for="plata">Plata:</label><br/>
 <input type=checkbox name="cash" value="cash">cash<br/>
 <input type=checkbox name="credit" value="credit">credit<br/><br/>

 <input type="submit" value="Trimite">
 </FORM>
<%
} } else {
%>
<p> Sesiune noua sau inexistenta atribut de sesiune auth</p>
<%
}
%>
 </BODY>
</HTML>