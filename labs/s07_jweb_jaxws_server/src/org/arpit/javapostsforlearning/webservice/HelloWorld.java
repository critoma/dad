package org.arpit.javapostsforlearning.webservice;

import javax.jws.WebMethod;
import javax.jws.WebService;
 
@WebService
public interface HelloWorld {
 
 @WebMethod public String helloWorld(String name);
}
