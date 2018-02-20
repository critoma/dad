https://www.tutorialspoint.com/apache_kafka/apache_kafka_installation_steps.htm

cd zookeeper
bin/zkServer.sh start
bin/zkCli.sh

root@stud-VirtualBox:/opt/software/kafka/kafka_2.11-0.10.1.0/jsamples# 
bin/kafka-server-start.sh config/server.properties

root@stud-VirtualBox:/opt/software/kafka/kafka_2.11-0.10.1.0/jsamples# 
bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1   --partitions 1 --topic Hello-Kafka

root@stud-VirtualBox:/opt/software/kafka/kafka_2.11-0.10.1.0/jsamples# 
bin/kafka-console-consumer.sh --zookeeper localhost:2181 --topic Hello-Kafka --from-beginning


root@stud-VirtualBox:/opt/software/kafka/kafka_2.11-0.10.1.0/jsamples# 
bin/kafka-console-producer.sh --broker-list localhost:9092 --topic Hello-Kafka

root@stud-VirtualBox:/opt/software/kafka/kafka_2.11-0.10.1.0/jsamples# cd jsamples/
root@stud-VirtualBox:/opt/software/kafka/kafka_2.11-0.10.1.0/jsamples# javac -cp "/opt/software/kafka/kafka_2.11-0.10.1.0/libs/*" *.java

root@stud-VirtualBox:/opt/software/kafka/kafka_2.11-0.10.1.0/jsamples# java -cp "/opt/software/kafka/kafka_2.11-0.10.1.0/libs/*":. SimpleProducer Hello-Kafka

root@stud-VirtualBox:/opt/software/kafka/kafka_2.11-0.10.1.0/jsamples# java -cp "/opt/software/kafka/kafka_2.11-0.10.1.0/libs/*":. SimpleConsumer Hello-Kafka
