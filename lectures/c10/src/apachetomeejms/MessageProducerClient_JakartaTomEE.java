// # /opt/software/jdk-17.0.2/bin/javac -cp .:/opt/software/apache-tomee-plume-9.0.0-M7/lib/* apachetomeejms/MessageProducerClient_JakartaTomEE.java
// # /opt/software/jdk-17.0.2/bin/java -cp .:/opt/software/apache-tomee-plume-9.0.0-M7/lib/* apachetomeejms/MessageProducerClient_JakartaTomEE 172.17.0.3 61617

package apachetomeejms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

import jakarta.jms.Connection;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.Destination;
import jakarta.jms.JMSException;
import jakarta.jms.MessageProducer;
import jakarta.jms.Session;
import jakarta.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

//import org.apache.activemq.broker.BrokerService;

public class MessageProducerClient_JakartaTomEE {

 public static Properties getProp(String ip, String port) {
	 Properties props = new Properties();
	 props.setProperty(Context.INITIAL_CONTEXT_FACTORY,
	 "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
	 //props.setProperty(Context.PROVIDER_URL, "tcp://localhost:61617");
         props.setProperty(Context.PROVIDER_URL, "tcp://"+ip+":"+port);
	 return props;
 }

// public static void initBroker() throws Exception {
//	 BrokerService broker = new BrokerService();
//	 // configure the broker
//	 broker.addConnector("tcp://localhost:61617");
//	 broker.start();
// }

 public static void main(String args[]) {

	 Connection connection = null;
	 try {
//		 initBroker();
		 InitialContext jndiContext = new InitialContext(getProp(args[0], args[1]));
		 ConnectionFactory connectionFactory = (ConnectionFactory) jndiContext
		 .lookup("ConnectionFactory");
		 connection = connectionFactory.createConnection();
		 connection.setClientID("durable");
		 Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		 Destination destination = session.createTopic("jms/topic/test");
		 MessageProducer producer = session.createProducer(destination);
		 TextMessage msg = session.createTextMessage();
		 msg.setText("Hello, This is JMS example !!");
		 BufferedReader reader = new BufferedReader(new InputStreamReader(
		 System.in));

	 while (true) {
		 System.out
		 .println("Enter Message to Topic or Press 'Q' for Close this Session");
		 String input = reader.readLine();
		 if ("Q".equalsIgnoreCase(input.trim())) {
		 	break;
		 }
		 msg.setText(input);
		 producer.send(msg);
	 }
	 } catch (JMSException e) {
	 	e.printStackTrace();
	 } catch (IOException e) {
	 	e.printStackTrace();
	 } catch (NamingException e) {
	 	e.printStackTrace();
	 } catch (Exception e) {
	 	e.printStackTrace();
	 } finally {
		 try {
		 	if (connection != null) {
		 		connection.close();
		 	}
		 } catch (JMSException e) {
		 	e.printStackTrace();
		 }
	 }

 }
}
