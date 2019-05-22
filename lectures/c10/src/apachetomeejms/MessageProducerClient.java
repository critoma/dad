package apachetomeejms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.broker.BrokerService;

public class MessageProducerClient {

 public static Properties getProp() {
	 Properties props = new Properties();
	 props.setProperty(Context.INITIAL_CONTEXT_FACTORY,
	 "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
	 props.setProperty(Context.PROVIDER_URL, "tcp://localhost:61617");
	 return props;
 }

 public static void initBroker() throws Exception {
	 BrokerService broker = new BrokerService();
	 // configure the broker
	 broker.addConnector("tcp://localhost:61617");
	 broker.start();
 }

 public static void main(String args[]) {

	 Connection connection = null;
	 try {
		 initBroker();
		 InitialContext jndiContext = new InitialContext(getProp());
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
