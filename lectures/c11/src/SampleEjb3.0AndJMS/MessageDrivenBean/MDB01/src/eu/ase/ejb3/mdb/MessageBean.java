package eu.ase.ejb3.mdb;

import javax.annotation.Resource;
import javax.ejb.*;
import javax.naming.*;
import javax.jms.*;


/**
 * The MessageBean class is a message-driven bean EJB3. 
 * instead modifying conf/tomee.xml, just add annotations here:
 * https://tomee.apache.org/latest/docs/jms-resources-and-mdb-container.html
 */
/*
@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationType",
                propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "destination",
                propertyValue = "queue/A") })
*/
@MessageDriven(
		activationConfig = { @ActivationConfigProperty(
				propertyName = "destination", propertyValue = "queueA"), @ActivationConfigProperty(
				propertyName = "destinationType", propertyValue = "javax.jms.Queue")
		})
public class MessageBean implements MessageListener {

    @Resource
    private MessageDrivenContext context;

    /**
     * Constructor, which is public and takes no arguments.
     */
    public MessageBean() {
        System.out.println("In MessageBean.MessageBean()");
    }


    /**
     * onMessage method, declared as public (but not final or 
     * static), with a return type of void, and with one argument
     * of type javax.jms.Message.
     *
     * Casts the incoming Message to a TextMessage and displays 
     * the text.
     *
     * @param inMessage    the incoming message
     */
    public void onMessage(Message inMessage) {
        TextMessage msg = null;

        try {
            if (inMessage instanceof TextMessage) {
                msg = (TextMessage) inMessage;
                System.out.println("MESSAGE BEAN: Message " + "received: " + msg.getText());
            } else {
                System.out.println("Message of wrong type: " + inMessage.getClass().getName());
            }
        } catch (JMSException e) {
            System.err.println("MessageBean.onMessage: " + "JMSException: " + e.toString());
	    context.setRollbackOnly();
        } catch (Throwable te) {
            System.err.println("MessageBean.onMessage: " + "Exception: " + te.toString());
        }
    }
    
}
