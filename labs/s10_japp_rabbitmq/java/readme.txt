# https://www.rabbitmq.com/docs/download
# docker or installation into OS
# https://github.com/rabbitmq/rabbitmq-tutorials/tree/main/java
# https://www.rabbitmq.com/tutorials/tutorial-one-java

# critoma@critoma-mac java % javac -cp .:./lib/amqp-client-5.21.0.jar:./lib/slf4j-api-2.0.13.jar:./lib/slf4j-simple-2.0.13.jar jrtest/Send.java
# critoma@critoma-mac java % java -cp .:./lib/amqp-client-5.21.0.jar:./lib/slf4j-api-2.0.13.jar:./lib/slf4j-simple-2.0.13.jar jrtest.Send
#  [x] Sent 'Hello World!'

# critoma@critoma-mac java % pwd
# /Users/critoma/Downloads/rabbitmqTest/java
cd /Users/critoma/Downloads/rabbitmqTest/java/lib
wget https://repo1.maven.org/maven2/com/rabbitmq/amqp-client/5.21.0/amqp-client-5.21.0.jar
wget https://repo1.maven.org/maven2/org/slf4j/slf4j-api/2.0.13/slf4j-api-2.0.13.jar
wget https://repo1.maven.org/maven2/org/slf4j/slf4j-simple/2.0.13/slf4j-simple-2.0.13.jar

javac -cp .:./lib/amqp-client-5.21.0.jar:./lib/slf4j-api-2.0.13.jar:./lib/slf4j-simple-2.0.13.jar jrtest/Send.java

java -cp .:./lib/amqp-client-5.21.0.jar:./lib/slf4j-api-2.0.13.jar:./lib/slf4j-simple-2.0.13.jar jrtest.Send

