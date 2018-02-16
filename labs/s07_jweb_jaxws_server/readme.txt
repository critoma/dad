// https://java2blog.com/jaxws-web-service-eclipse-tutorial/
// https://java2blog.com/jaxws-webservice-deployement-on-tomcat/


// After creating HelloWorld.java interface and HelloWorldImpl.java:
/* Now You need do following, 
1) open command prompt.Go to your eclipse workspace(For example: cd E:/arpit/workspace) 
2) then go to project(For example if project name is jaxws then "cd jaxws") 
3) paste following "wsgen -s src -d build/classes -cp build/classes org.arpit.javapostsforlearning.webservice.HelloWorldImpl" then enter 

cd %project_home%
wsgen -s src -d build/classes -cp build/classes org.arpit.javapostsforlearning.webservice.HelloworldImpl
*/

export JAVA_HOME=/opt/software/java/jdks/jdk1.8.0_161
export PATH=$JAVA_HOME/bin:/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin:/usr/games
export CLASSPATH=.:$JAVA_HOME/jre/lib
export DADLABS=/home/stud/dad/labs

cd $DADLABS
cd s07_jweb_jaxws_server
wsgen -s src -d build/classes -cp build/classes org.arpit.javapostsforlearning.webservice.HelloWorldImpl


// http://localhost:8080/s07_jweb_jaxws_server/HelloWorldWS
// http://127.0.0.1:8080/s07_jweb_jaxws_server/HelloWorldWS
// http://127.0.0.1:8080/s07_jweb_jaxws_server/HelloWorldWS?wsdl