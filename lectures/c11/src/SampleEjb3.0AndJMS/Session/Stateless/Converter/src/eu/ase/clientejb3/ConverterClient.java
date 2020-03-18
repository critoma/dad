package eu.ase.clientejb3;

import eu.ase.ejb3.ConverterRemote;
import eu.ase.ejb3.Converter;


import java.math.BigDecimal;
//import java.util.Hashtable;

import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;

public class ConverterClient {
    public static void main(String[] args) {
        try {
		//Hashtable env = new Hashtable();
		//env.put(Context.INITIAL_CONTEXT_FACTORY,"org.jnp.interfaces.NamingContextFactory");
		///env.put(Context.INITIAL_CONTEXT_FACTORY,"org.jboss.naming.NamingContextFactory");
		//env.put(Context.PROVIDER_URL,"jnp://127.0.0.1:1099");
		//env.put(Context.URL_PKG_PREFIXES, "org.jboss.naming:org.jnp.interfaces" );
		
		//Context ctx = new InitialContext(env);

	    	//Converter currencyConverter = (Converter) ctx.lookup("ConverterBean/remote");


		Properties p = new Properties();
	    	p.put("java.naming.factory.initial",
		 "org.apache.openejb.client.RemoteInitialContextFactory");
		p.put("java.naming.provider.url", "http://localhost:8080/tomee/ejb");
            
		InitialContext ctx = new InitialContext(p);

		//Properties p = new Properties();
		//p.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.apache.openejb.core.LocalInitialContextFactory");
		//InitialContext ctx = new InitialContext(p);

		NamingEnumeration<NameClassPair> list = ctx.list("");
		while (list.hasMore()) {
  			System.out.println(list.next().getName());
		}

		//Converter currencyConverter = (Converter) ctx.lookup("java:ConverterBeanRemote");
		
		Converter currencyConverter = (Converter) ctx.lookup("ConverterBeanRemote");
		
            BigDecimal param = new BigDecimal("100.00");
            BigDecimal amount = currencyConverter.dollarToYen(param);

            System.out.println(amount);
            amount = currencyConverter.yenToEuro(param);
            System.out.println(amount);

            System.exit(0);
        } catch (Exception ex) {
            System.err.println("Caught an unexpected exception!");
            ex.printStackTrace();
        }
    }
}
