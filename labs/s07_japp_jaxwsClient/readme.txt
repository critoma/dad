# cd %project_home/src
# wsimport -s . http://localhost:8080/WS/HelloWorld?wsdl

export JAVA_HOME=/opt/software/java/jdks/jdk1.8.0_161
export PATH=$JAVA_HOME/bin:/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin:/usr/games
export CLASSPATH=.:$JAVA_HOME/jre/lib
export DADLABS=/home/stud/dad/labs

cd $DADLABS
cd s07_japp_jaxwsClient/src
wsimport -s . http://127.0.0.1:8080/WS/HelloWorld?wsdl