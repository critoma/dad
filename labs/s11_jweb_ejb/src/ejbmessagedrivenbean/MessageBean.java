package ejbmessagedrivenbean;

import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.ejb.MessageDrivenContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
//import javax.naming.InitialContext;
import javax.jms.Topic;

//import org.apache.activemq.broker.BrokerService;


/**
 * Message-Driven Bean implementation class for: MessageBean
 */
/*
@MessageDriven(
		activationConfig = { @ActivationConfigProperty(
				propertyName = "destination", propertyValue = "jms/topic/test1"), @ActivationConfigProperty(
				propertyName = "destinationType", propertyValue = "javax.jms.Topic")
		}, 
		mappedName = "jms/topic/test1")
		*/
//@MessageDriven
@MessageDriven(
		activationConfig = { @ActivationConfigProperty(
				propertyName = "destination", propertyValue = "jms/topic/test1"), @ActivationConfigProperty(
				propertyName = "destinationType", propertyValue = "javax.jms.Topic")
		})
public class MessageBean implements MessageListener {

	@Resource
	private MessageDrivenContext mdc;
	//@Resource(name = "jms/topic/test1")
    //private Topic topic;
	
    /**
     * Default constructor. 
     */
    public MessageBean() {
        System.out.println("Constructor EJB3 MDB");
    }
	
	/**
     * @see MessageListener#onMessage(Message)
     */
    public void onMessage(Message inMessage) {
        //System.out.println("MDB::onMessage()");
        TextMessage msg = null;
        try {
        	if(inMessage instanceof TextMessage) {
        		msg = (TextMessage) inMessage;
        		System.out.println("MDB EJB3 - asynch received = "+msg.getText());
        	} else {
        		System.out.println("Message of wrong type");
        	}
        } catch(JMSException jmse) {
        	jmse.printStackTrace();
        	throw new IllegalStateException(jmse);
        } catch(Throwable t) {
        	t.printStackTrace();
        }
    }

}
