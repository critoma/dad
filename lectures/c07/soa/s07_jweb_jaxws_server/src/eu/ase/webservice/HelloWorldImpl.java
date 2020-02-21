package eu.ase.webservice;

import javax.jws.WebService;

@WebService(endpointInterface="eu.ase.webservice.HelloWorld")
public class HelloWorldImpl implements HelloWorld {
	public String helloWorld(String name) {
		return "Hello world from " + name;
	}
}
