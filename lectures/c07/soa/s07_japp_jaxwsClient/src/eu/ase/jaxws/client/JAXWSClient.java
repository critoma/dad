package eu.ase.jaxws.client;

import eu.ase.jaxws.HelloWorld;
import eu.ase.jaxws.HelloWorldImplService;
 
public class JAXWSClient {

    public static void main(String[] args) {
        
        HelloWorldImplService helloWorldService = new HelloWorldImplService();
        HelloWorld helloWorld = helloWorldService.getHelloWorldImplPort();
        System.out.println(helloWorld.helloWorld("John"));
    }
}
