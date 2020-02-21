package eu.ase.jaxws;

import javax.xml.ws.Endpoint;

public class HelloWorldWSPub {
	public static void main(String ... args ) {
		Endpoint.publish("http://127.0.0.1:8080/WS/HelloWorld", 
				new HelloWorldImpl());
	}
}
