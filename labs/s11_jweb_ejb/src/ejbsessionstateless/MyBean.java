package ejbsessionstateless;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 * Session Bean implementation class MyBean
 */
@Stateless
@LocalBean
public class MyBean {

    public MyBean() {
    }
    
    public String doSomething() {
    	return "Hello from EJB";
    }

}
