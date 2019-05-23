package ejbsessionstateless;

import java.rmi.RemoteException;
import java.util.Properties;

import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.activemq.broker.BrokerService;


@Stateless
@LocalBean
public class Publisher {
	private int acc;
	
	@Resource
    private ConnectionFactory connectionFactory;
	
	//@Resource(name = "jms/topic/test1")
    private Topic testTopic;
	
    public Publisher() {
    	//BrokerService broker = new BrokerService();
        try {
//			broker.addConnector("tcp://localhost:61616");
//			broker.start();
			
			Properties props = new Properties();
			props.setProperty(Context.INITIAL_CONTEXT_FACTORY, 
					"org.apache.activemq.jndi.ActiveMQInitialContextFactory");
			props.setProperty(Context.PROVIDER_URL, "tcp://localhost:61616");
			InitialContext jndi = new InitialContext(props);
			connectionFactory = (ConnectionFactory)jndi.lookup("ConnectionFactory");
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

	public boolean publishNews() {
		try {
	        Connection connection = connectionFactory.createConnection();
	        connection.start();

	        // Create a Session
	        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
	        testTopic = session.createTopic("jms/topic/test1");
	        
	        // Create a MessageProducer from the Session to the Topic or Queue
	        MessageProducer producer = session.createProducer(testTopic);
	        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

	        acc++;
	        // Create a message
	        TextMessage message = session.createTextMessage("Hello World message " + acc + " into JMS topic!");

	        // Tell the producer to send the message
	        producer.send(message);
	        return true;
		} catch(JMSException jmse) {
			jmse.printStackTrace();
			return false;
		}
	}
}
