1. Set environment

set JAVA_HOME=C:\Software\Java\JDKs\jdk1.5.0_09
set CATALINA_HOME=C:\Software\Java\ApacheProjects\apache-tomcat-6.0.16
set PATH=.;%JAVA_HOME%\bin;%CATALINA_HOME%\bin
set CLASSPATH=.;%JAVA_HOME%\jre\lib\rt.jar;%CATALINA_HOME%\lib\servlet-api.jar;%CATALINA_HOME%\lib\jsp-api.jar

d:
cd D:\Temp\DAD\Lecture\c10\src\S11_JSP\Struts1

copy .\struts-blank-1.3.10.war %CATALINA_HOME%\webapps\struts-blank-1.3.10.war

1. Test Struts 1

http://127.0.0.1:8080/struts-blank-1.3.10/index.jsp

2. Development in Struts 1
