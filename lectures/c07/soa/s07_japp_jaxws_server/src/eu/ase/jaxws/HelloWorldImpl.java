package eu.ase.jaxws;

import javax.jws.WebService;

@WebService(endpointInterface="eu.ase.jaxws.HelloWorld")
public class HelloWorldImpl {
	public String helloWorld(String name) {
		return "Hello from WS web method to " + name;
	}

}
