package org.arpit.javapostsforlearning.webservice;

import javax.jws.WebMethod;
import javax.jws.WebService;

// Sometimes in Eclipse IDE are problems with the access rules:
// The solution is to change the access restrictions. 
// Go to the properties of your Java project, i.e. by selecting "Properties" from the context menu of the project in the "Package Explorer". 
// There, go to "Java Build Path", tab "Libraries". There, expand the library entry, select "Access rules", "Edit..." and "Add..." a "Resolution: Accessible" with a corresponding rule pattern. 
// For me that was "javax/smartcardio/**", for you it might instead be "com/apple/eawt/**" or "javax/**".

// http://localhost:8080/WS/HelloWorld?wsdl
// http://127.0.0.1:8080/WS/HelloWorld?wsdl

// https://java2blog.com/jaxws-web-service-eclipse-tutorial/

@WebService
public interface HelloWorld {
	@WebMethod 
	public String helloWorld(String name);
}
