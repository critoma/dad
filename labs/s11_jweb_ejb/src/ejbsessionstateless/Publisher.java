package ejbsessionstateless;

import java.rmi.RemoteException;

import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

/**
 * Session Bean implementation class Publisher
 */
@Stateless
@LocalBean
public class Publisher implements PublisherRemote, PublisherLocal {

	private int acc;
	
	@Resource
    private ConnectionFactory connectionFactory;
	
	@Resource(name = "jms/topic/test1")
    private Topic testTopic;
	
    public Publisher() {
    	
    }

	@Override
	public void publishNews() throws RemoteException {
		try {
	        Connection connection = connectionFactory.createConnection();
	        connection.start();

	        // Create a Session
	        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

	        // Create a MessageProducer from the Session to the Topic or Queue
	        MessageProducer producer = session.createProducer(testTopic);
	        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

	        acc++;
	        // Create a message
	        TextMessage message = session.createTextMessage("Hello World message " + acc + " into JMS topic!");

	        // Tell the producer to send the message
	        producer.send(message);

	        //...
		} catch(JMSException jmse) {
			jmse.printStackTrace();
		}
	}

}
