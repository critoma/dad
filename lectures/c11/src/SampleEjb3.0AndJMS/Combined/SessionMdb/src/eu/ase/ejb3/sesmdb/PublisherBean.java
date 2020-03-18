package eu.ase.ejb3.sesmdb;

import javax.annotation.Resource;
import java.rmi.RemoteException; 
import java.util.*;
import javax.ejb.*;
import javax.naming.*;
import javax.jms.*;

@Stateless
public class PublisherBean implements PublisherRemote, PublisherLocal {
    @Resource
    SessionContext      sc = null;

    String topicName = "dynamicTopics/testTopicLecture";
    private Hashtable<String, String> env = new Hashtable<String, String>();
    Context                 jndiContext = null;
    TopicConnectionFactory  topicConnectionFactory = null;
    TopicConnection     topicConnection = null;
    Topic               topic = null;
    final static String messageTypes[] = {"Nation/World", "Metro/Region", "Business", "Sports", "Living/Arts", "Opinion"};

    public PublisherBean() {
	
	//env.put(Context.INITIAL_CONTEXT_FACTORY,"org.jnp.interfaces.NamingContextFactory");
	//env.put(Context.PROVIDER_URL,"jnp://127.0.0.1:1099");
	//env.put(Context.URL_PKG_PREFIXES, "org.jboss.naming:org.jnp.interfaces" );
	
	env.put(Context.INITIAL_CONTEXT_FACTORY,"org.apache.openejb.client.RemoteInitialContextFactory");
	env.put(Context.PROVIDER_URL,"http://localhost:8080/tomee/ejb");
	

	
	/* 
         * Create a JNDI API InitialContext object.
         */
        try {
            jndiContext = new InitialContext(env);
        } catch (NamingException e) {
            System.out.println("Could not create JNDI API " + "context: " + e.toString());
        }
        
        /* 
         * Look up connection factory and queue.  If either does
         * not exist, exit.
         */
        try {
            topicConnectionFactory = (TopicConnectionFactory)jndiContext.lookup("ConnectionFactory");
            topic = (Topic) jndiContext.lookup(topicName);
        } catch (NamingException e) {
            System.out.println("JNDI API lookup failed: " + e.toString());
        }
        System.out.println("In PublisherBean() (constructor) - Topic Connection created with success for topic = "+topicName+"!");
    }


    /**
     * Chooses a message type by using the random number
     * generator found in java.util.  Called by publishNews().
     *
     * @return   the String representing the message type
     */
    private String chooseType() {
        int    whichMsg;
        Random rgen = new Random();
       
        whichMsg = rgen.nextInt(messageTypes.length);
        return messageTypes[whichMsg];
    }

    /**
     * Creates TopicSession, publisher, and message.  Publishes 
     * messages after setting their NewsType property and using
     * the property value as the message text. Messages are
     * received by MessageBean, a message-driven bean that uses a
     * message selector to retrieve messages whose NewsType
     * property has certain values.
     */
    public void publishNews() throws EJBException {
        TopicSession   topicSession = null;
        TopicPublisher topicPublisher = null;
        TextMessage    message = null;
        int            numMsgs = messageTypes.length * 3;
        String         messageType = null;

        try {
		//pay attention to transactioned publishing
            topicConnection = topicConnectionFactory.createTopicConnection();
            topicSession = topicConnection.createTopicSession(false, TopicSession.AUTO_ACKNOWLEDGE);
            topicPublisher = topicSession.createPublisher(topic);

            message = topicSession.createTextMessage();
            for (int i = 0; i < numMsgs; i++) {
                messageType = chooseType();
                message.setStringProperty("NewsType", messageType);
                message.setText("Item " + i + ": " + messageType);
                System.out.println("PUBLISHER: Setting " + "message text to: " + message.getText());
                topicPublisher.publish(message);
            }
        } catch (Throwable t) {
            // JMSException could be thrown
            System.err.println("PublisherBean.publishNews: " + "Exception: " + t.toString());
		//container managed transactions
            sc.setRollbackOnly();
        } finally {
            if (topicSession != null) {
                try {
                    topicSession.close();
                } catch (JMSException e) {}
            }
        }
    }

}
