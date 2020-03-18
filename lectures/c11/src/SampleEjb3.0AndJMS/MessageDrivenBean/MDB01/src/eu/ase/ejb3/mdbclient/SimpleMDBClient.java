package eu.ase.ejb3.mdbclient;

import javax.jms.*;
import javax.naming.*;

import java.util.Properties;


/**
 * The SimpleClient class sends several messages to a queue.
 */
public class SimpleMDBClient {

    /**
     * Main method.
     */
    public static void main(String[] args) {
        Context                 jndiContext = null;
        QueueConnectionFactory  queueConnectionFactory = null;
        QueueConnection         queueConnection = null;
        QueueSession            queueSession = null;
        Queue                   queue = null;
        QueueSender             queueSender = null;
        TextMessage             message = null;
        int                     NUM_MSGS = 0;
	String 			queueName = null;
        
	  if ( (args.length < 1) || (args.length > 2) ) {
            System.out.println("Usage: java SimpleClient " +
                "<queue-name> [<number-of-messages>]\r\n e.g. <queue-name> = queue/testQueue");
            System.exit(1);
        }
        queueName = new String(args[0]);
        System.out.println("Queue name is " + queueName);
        if (args.length == 2){
            NUM_MSGS = (new Integer(args[1])).intValue();
        } else {
            NUM_MSGS = 1;
        }

        /* 
         * Create a JNDI API InitialContext object.
         */
        try {
            //jndiContext = new InitialContext();
//Properties props = new Properties();
//props.put(Context.INITIAL_CONTEXT_FACTORY, "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
//    props.put(Context.URL_PKG_PREFIXES, "org.apache.activemq.jndi");
//    props.put(Context.INITIAL_CONTEXT_FACTORY, "org.apache.openejb.client.RemoteInitialContextFactory");
//props.put(Context.PROVIDER_URL, "tcp://localhost:61616");

Properties props = new Properties();
props.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
props.setProperty(Context.PROVIDER_URL, "tcp://localhost:61616");
		jndiContext = new InitialContext(props);
NamingEnumeration<NameClassPair> list = jndiContext.list("");
		while (list.hasMore()) {
  			System.out.println(list.next().getName());
		}
        } catch (NamingException e) {
            System.out.println("Could not create JNDI API " + "context: " + e.toString());
            System.exit(1);
        }


        
        /* 
         * Look up connection factory and queue.  If either does
         * not exist, exit.
         */
        try {
            queueConnectionFactory = (QueueConnectionFactory)jndiContext.lookup("QueueConnectionFactory");

            queue = (Queue) jndiContext.lookup(queueName);

        } catch (NamingException e) {
            System.out.println("JNDI API lookup failed: " + e.toString());
            System.exit(1);
        }

        /*
         * Create connection.
         * Create session from connection; false means session is
         * not transacted.
         * Create sender and text message.
         * Send messages, varying text slightly.
         * Finally, close connection.
         */
        try {
            queueConnection = queueConnectionFactory.createQueueConnection();

            queueSession = queueConnection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
            
	    queueSender = queueSession.createSender(queue);

            message = queueSession.createTextMessage();
            for (int i = 0; i < NUM_MSGS; i++) {
                message.setText("This is message " + (i + 1));
                System.out.println("Sending message: " + message.getText());
                queueSender.send(message);
            }
        } catch (JMSException e) {
            System.out.println("Exception occurred: " + e.toString());
        } finally {
            if (queueConnection != null) {
                try {
                    queueConnection.close();
                } catch (JMSException e) {}
            }
            System.exit(0);
        }
    }
}
