package eu.ase.ejb3.statefulclient;

import eu.ase.ejb3.stateful.HTMLReader;

import javax.ejb.NoSuchEJBException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;

import java.util.Properties;

import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;

public class HTMLReaderClient {
    public static void main(String[] args) {
        try {
		Properties p = new Properties();
	    	p.put("java.naming.factory.initial",
		 "org.apache.openejb.client.RemoteInitialContextFactory");
		p.put("java.naming.provider.url", "http://localhost:8080/tomee/ejb");
            
		//InitialContext ctx = new InitialContext(p);

            	InitialContext initial = new InitialContext(p);
		NamingEnumeration<NameClassPair> list = initial.list("");
		while (list.hasMore()) {
  			System.out.println(list.next().getName());
		}
		
            //Object objref = initial.lookup("HTMLReaderBean/remote");
	//Object objref = initial.lookup("HTMLReaderBeanRemote");
HTMLReader htmlReader = (HTMLReader) initial.lookup("HTMLReaderBeanRemote");


            //HTMLReader htmlReader = (HTMLReader) PortableRemoteObject.narrow(objref, HTMLReader.class);


	    StringBuffer contents = null;
	    long before = 0, after = 0;

	    for(int i = 0; i < args.length; i++) {
			before = System.currentTimeMillis();
			contents = htmlReader.getContents(args[i]);
			after = System.currentTimeMillis();

			System.out.println("\n The contents "+i+" of the HTML page in "+(after-before)+" miliseconds is:\n");
            System.out.print(contents);		
	    }

	    System.out.println("Checkout");
	    // uncomment checkput and will crash because checkout delete the EJB
            //htmlReader.checkout();

//if (htmlReader !=null) return;
      	    System.out.println("Should throw an object not found exception by invoking on HTMLReader ejb object after @Remove method");
            try {
				htmlReader.getContents(args[0]);
      	    } catch (javax.ejb.NoSuchEJBException nsejbe) {
	         System.out.println("Successfully caught no such object exception.\n");
			nsejbe.printStackTrace();
      	    }

            System.exit(0);
        } catch (Exception ex) {
            System.err.println("Caught an unexpected exception!");
            ex.printStackTrace();
        }
    }
}
