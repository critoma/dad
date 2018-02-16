
package org.arpit.javapostsforlearning.webservice;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.arpit.javapostsforlearning.webservice package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _HelloWorld_QNAME = new QName("http://webservice.javapostsforlearning.arpit.org/", "helloWorld");
    private final static QName _HelloWorldResponse_QNAME = new QName("http://webservice.javapostsforlearning.arpit.org/", "helloWorldResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.arpit.javapostsforlearning.webservice
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link HelloWorldResponse }
     * 
     */
    public HelloWorldResponse createHelloWorldResponse() {
        return new HelloWorldResponse();
    }

    /**
     * Create an instance of {@link HelloWorld_Type }
     * 
     */
    public HelloWorld_Type createHelloWorld_Type() {
        return new HelloWorld_Type();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link HelloWorld_Type }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.javapostsforlearning.arpit.org/", name = "helloWorld")
    public JAXBElement<HelloWorld_Type> createHelloWorld(HelloWorld_Type value) {
        return new JAXBElement<HelloWorld_Type>(_HelloWorld_QNAME, HelloWorld_Type.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link HelloWorldResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.javapostsforlearning.arpit.org/", name = "helloWorldResponse")
    public JAXBElement<HelloWorldResponse> createHelloWorldResponse(HelloWorldResponse value) {
        return new JAXBElement<HelloWorldResponse>(_HelloWorldResponse_QNAME, HelloWorldResponse.class, null, value);
    }

}
