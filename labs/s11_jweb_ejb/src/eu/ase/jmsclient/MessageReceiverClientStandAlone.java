package eu.ase.jmsclient;

import javax.jms.JMSException;
//import javax.jms.MessageConsumer;
import javax.jms.Session;

//import javax.jms.Connection;
//import javax.jms.ConnectionFactory;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;

import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import java.util.Properties;

import java.io.IOException;
import java.io.InputStreamReader;


public class MessageReceiverClientStandAlone {

	protected static final String url = "tcp://localhost:61616";
	 public static void main(String[] args) {
		String topicName = null;
		Context jndiContext = null;
		TopicConnectionFactory topicConnectionFactory = null;
		TopicConnection topicConnection = null;
		TopicSession topicSession = null;
		Topic topic = null;
		TopicSubscriber topicSubscriber = null;
		TextListener topicListener = null;
		//TextMessage message = null;
		InputStreamReader inputStreamReader = null;
		char answer = '\0';
		/*	
			if(args.length != 1) {
				System.out.println("Usage: java SimpleTopicSubscriber <topic-name>");
				System.exit(1);
			}
		*/	
		//topicName = new String(args[1]);
	    topicName = new String("jms/topic/test1");
		System.out.println("Topic name = "+topicName);
			
		//JNDI context look-up + look-up factory & topic
		try {
	         Properties props = new Properties();
		 	 props.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
		 	 props.setProperty(Context.PROVIDER_URL, url);
			//props.setProperty(Context.PROVIDER_URL, args[0]);

			jndiContext = new InitialContext(props);
			//jndiContext = new InitialContext();
				
			topicConnectionFactory = (TopicConnectionFactory)jndiContext.lookup("ConnectionFactory");
			//topic = (Topic)jndiContext.lookup(topicName);
				
		} catch(NamingException ne) {
			ne.printStackTrace();
			System.exit(2);
		}
			
		try {
			topicConnection = topicConnectionFactory.createTopicConnection();
			topicSession = topicConnection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
			topic = topicSession.createTopic("jms/topic/test1");
			topicSubscriber = topicSession.createSubscriber(topic);
				
			topicListener = new TextListener();
			topicSubscriber.setMessageListener(topicListener);
			topicConnection.start();
				
				System.out.println("To end program, insert q + CR/LF");
				inputStreamReader = new InputStreamReader(System.in);
				while(!(answer == 'q')) {
					try {
						answer = (char) inputStreamReader.read();
					} catch(IOException ioe) {
						ioe.printStackTrace();
					}
				}
			} catch(JMSException jmse) {
				jmse.printStackTrace();
			} finally {
				if(topicConnection != null) {
					try {
						topicConnection.close();
					} catch (JMSException e) {
						e.printStackTrace();
					}
				}
			}
		}

}

class TextListener implements MessageListener {

	@Override
	public void onMessage(Message message) {
			TextMessage msg = null;
			
			try {
				if(message instanceof TextMessage) {
					msg = (TextMessage)message;
					System.out.println("Received message = "+msg.getText());
				} else {
					System.out.println("Binary messsage!");
				}
			} catch(JMSException jmse) {
				jmse.printStackTrace();
			} catch(Throwable t) {
				t.printStackTrace();
			}
	}

}

