<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html><body>
<p>Checking prime numbers:</p>
<c:set var="upperLimit" value="${20}"/>
<c:forEach var="i" begin="${3}" end="${upperLimit}">
 <c:set var="isPrime" value="${true}"/>
 <c:forEach var="j" begin="${2}" end="${i-1}">
  <c:if test="${i%j == 0}">
   <c:set var="isPrime" value="${false}"/>
   <!-- We should break the loop here -->
  </c:if>
 </c:forEach>
 <c:choose>
  <c:when test="${isPrime}">
   <c:out value="${i} is a prime number."/><br/>
  </c:when>
  <c:otherwise>
   <c:out value="${i} is a not prime number."/><br/>
  </c:otherwise>
 </c:choose>
</c:forEach>
</body></html>
