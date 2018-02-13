set JAVA_HOME=C:\Program Files\Java\jdk1.6.0
set CATALINA_HOME=C:\Program Files\Software\apache-tomcat-6.0.16
set PATH=.;%JAVA_HOME%\bin;%CATALINA_HOME%\bin
set CLASSPATH=.;%JAVA_HOME%\jre\lib\rt.jar;%CATALINA_HOME%\lib\servlet-api.jar

//radcom
set JAVA_HOME=C:\Program Files\Java\jdk1.6.0_11
set CATALINA_HOME=C:\Program Files\Apache Software Foundation\Tomcat 6.0
set PATH=.;%JAVA_HOME%\bin;%CATALINA_HOME%\bin
set CLASSPATH=.;%JAVA_HOME%\jre\lib\rt.jar;%CATALINA_HOME%\lib\servlet-api.jar

//C
set JAVA_HOME=C:\Software\Java\JDKs\jdk1.5.0_09
set CATALINA_HOME=C:\Software\Java\ApacheProjects\apache-tomcat-6.0.16
set PATH=.;%JAVA_HOME%\bin;%CATALINA_HOME%\bin
set CLASSPATH=.;%JAVA_HOME%\jre\lib\rt.jar;%CATALINA_HOME%\lib\servlet-api.jar

d:
cd D:\Temp\DAD2008\Login

c:
cd C:\temp\DAD2008\Lecture\Login
cd WEB-INF/classes

javac eu/ase/beans/FormularBean.java
javac eu/ase/beans/SelectUSR.java
javac eu/ase/servlets/ServletAuth.java


<?xml version="1.0" encoding="ISO-8859-1"?>

<web-app xmlns="http://java.sun.com/xml/ns/j2ee"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://java.sun.com/xml/ns/j2ee http://java.sun.com/xml/ns/j2ee/web-app_2_4.xsd"
    version="2.4">
  <display-name>Servlets Labs</display-name>
  
  <servlet>
        <servlet-name>S1</servlet-name>
        <servlet-class>eu.ase.servlets.ServletAuth</servlet-class>
  </servlet>

  <servlet-mapping>
        <servlet-name>
            S1
        </servlet-name>
        <url-pattern>
            /ServletAuth
        </url-pattern>
   </servlet-mapping>
</web-app>


mkdir %CATALINA_HOME%\webapps\Login

copy .\p1.html %CATALINA_HOME%\webapps\Login\p1.html
copy .\p1.jsp %CATALINA_HOME%\webapps\Login\p1.jsp
copy .\auth.jsp %CATALINA_HOME%\webapps\Login\auth.jsp

mkdir %CATALINA_HOME%\webapps\Login\WEB-INF\classes\eu\ase\beans
copy .\eu\ase\beans\FormularBean.class %CATALINA_HOME%\webapps\Login\WEB-INF\classes\eu\ase\beans\FormularBean.class
copy .\eu\ase\beans\SelectUSR.class %CATALINA_HOME%\webapps\Login\WEB-INF\classes\eu\ase\beans\SelectUSR.class


Control Panel -> Administrative Tools -> Odbc Connection -> FileDNS ...Add... S10 <-> bdMembri.mdb ;bdLogin.mdb



http://127.0.0.1:8080/Login/ServletAuth

http://127.0.0.1:8080/Login/p1.html


