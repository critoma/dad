package eu.ase.ejb3.sesmdb;

import javax.annotation.Resource;
import javax.ejb.*;
import javax.naming.*;
import javax.jms.*;


/**
 * The MessageBean class is a message-driven bean EJB3.  
 */
@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationType",
                propertyValue = "javax.jms.Topic"),
        @ActivationConfigProperty(propertyName = "destination",
                propertyValue = "dynamicTopics/testTopicLecture") })
public class MessageBean implements MessageListener {

    @Resource
    private MessageDrivenContext mdc;

    /**
     * Constructor, which is public and takes no arguments.
     */
    public MessageBean() {
        System.out.println("In MessageBean.MessageBean() EJB3");
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
	    mdc.setRollbackOnly();
        } catch (Throwable te) {
            System.err.println("MessageBean.onMessage: " + "Exception: " + te.toString());
        }
    }
    
}

