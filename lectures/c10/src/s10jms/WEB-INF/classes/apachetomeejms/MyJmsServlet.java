package apachetomeejms;

import javax.annotation.Resource; 

import javax.servlet.annotation.WebServlet;

import javax.servlet.http.HttpServlet; 
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;

import javax.jms.*;
/*
import javax.jms.Topic; 
//import javax.jms.Queue; 
import javax.jms.ConnectionFactory;
import javax.jms.MessageProducer;
*/
import java.io.PrintWriter;
import java.io.IOException;

@WebServlet("/MyJmsServlet")
public class MyJmsServlet extends HttpServlet {

    private int acc;

    @Resource(name = "jms/topic/test")
    private Topic testTopic;

    //@Resource(name = "bar")
    //private Queue barQueue;

    @Resource
    private ConnectionFactory connectionFactory;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //...
	response.setContentType("text/html");
    	PrintWriter out = response.getWriter();
	out.println("<HTML><BODY><P>A message will be published into the JMS topic!</P>");
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
	out.println("<p>The message 'Hello World message into JMS topic!' has been published into the topic with name: jms/topic/test</p></BODY></HTML>");
    }

}
