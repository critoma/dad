package eu.ase.ejb3.sesmdbclient;

import java.util.Hashtable;
import java.util.Properties;
import javax.naming.*;
import javax.rmi.PortableRemoteObject;
import javax.jms.*;
import eu.ase.ejb3.sesmdb.Publisher;

/**
 * The MyAppClient class is the client program for this JEE
 * application.  It obtains a reference to the home interface
 * of the Publisher enterprise bean and creates an instance of
 * the bean.  After calling the publisher's publishNews method
 * twice, it removes the bean.
 */
public class MyAppClient {

    public static void main (String[] args) {
        MyAppClient client = new MyAppClient();
        client.doTest();
        System.exit(0);
    }
    
    public void doTest() {
        
        try {
            
            //Context ic = new InitialContext();
	    //Hashtable<String, String> env = new Hashtable<String, String>();
	    //env.put(Context.INITIAL_CONTEXT_FACTORY,"org.jnp.interfaces.NamingContextFactory");
		////env.put(Context.INITIAL_CONTEXT_FACTORY,"org.jboss.naming.NamingContextFactory");
	    //env.put(Context.PROVIDER_URL,"jnp://127.0.0.1:1099");
	    //env.put(Context.URL_PKG_PREFIXES, "org.jboss.naming:org.jnp.interfaces" );

            //Context ic = new InitialContext(env);

	    Properties p = new Properties();
	    p.put("java.naming.factory.initial",
		 "org.apache.openejb.client.RemoteInitialContextFactory");
	    p.put("java.naming.provider.url", "http://localhost:8080/tomee/ejb");
            
	    InitialContext ic = new InitialContext(p);

		NamingEnumeration<NameClassPair> list = ic.list("");
		while (list.hasMore()) {
  			System.out.println(list.next().getName());
		}

	
            Publisher phr = (Publisher)ic.lookup("PublisherBeanRemote");
            System.err.println("Got the EJB");
            phr.publishNews();
            phr.publishNews();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
