// EmployeeClient.java

package eu.ase.ejb3_jpa;
/*
import java.io.*;
import java.util.*;
import java.rmi.RemoteException;
import javax.ejb.*;
import javax.naming.*;
import javax.rmi.PortableRemoteObject;
import eu.ase.ejb2.cmp.Employee;
import eu.ase.ejb2.cmp.EmployeeHome;
*/

import javax.naming.*;
import javax.persistence.*;
import java.util.*;
import eu.ase.ejb3_jpa.Employee;

/*
 * A simple client for accessing an BMP-EJB.
 */

public class EmployeeClient
{
  public static void main(String[] args)
  {

    System.out.println("EmployeeClient.main(): client started..."); 
    try {
      /**
       * Create access to the naming context. // see jndi.properties file
       */
	Hashtable<String, String> env = new Hashtable<String, String>();
	env.put(Context.INITIAL_CONTEXT_FACTORY,"org.jnp.interfaces.NamingContextFactory");
	env.put(Context.PROVIDER_URL,"jnp://127.0.0.1:1099");
	env.put(Context.URL_PKG_PREFIXES, "org.jboss.naming:org.jnp.interfaces" );

        Context ctx = new InitialContext(env);
	

	// like discussed with regards to SessionFactory, an EntityManagerFactory is set up once for an application
	// 		IMPORTANT: notice how the name here matches the name we gave the persistence-unit in persistence.xml!

	EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("eu.ase.ejb3_jpa");

	//create employees with JPA
	EntityManager entityManager = entityManagerFactory.createEntityManager();
	entityManager.getTransaction().begin();
	entityManager.persist( new Employee(102, "Gigel-Mohicanu", (float)877.23));
	entityManager.persist( new Employee(103, "Ionel-Vasilescu", (float)899.23));
	entityManager.getTransaction().commit();
	entityManager.close();

	//obtain list of employees with JPA
	entityManager = entityManagerFactory.createEntityManager();
	entityManager.getTransaction().begin();
	//List<Employee> result = entityManager.createQuery( "from Employee", Employee.class ).getResultList();
	List<Employee> result = entityManager.createQuery( "from Employee").getResultList();
	for ( Employee employee : result ) {
	    System.out.println( "Employee (" + employee.toString() + ") ");
	}
	entityManager.getTransaction().commit();
	entityManager.close();

	entityManagerFactory.close();
    } catch(Exception e) {
	e.printStackTrace();      
	//System.err.println("Exception: " + e.getMessage());
    }
/*

	    Converter currencyConverter = (Converter) ctx.lookup("ConverterBean/remote");
  
    Object homeObject = context.lookup("ejb/JNDIEmployeeBeanCMP");
      System.out.println("EmployeeClient.main(): bean found...");
      // Narrow the reference to a EmployeeHome.
      EmployeeHome home = (EmployeeHome) PortableRemoteObject.narrow(homeObject, EmployeeHome.class);

      System.out.println("EmployeeClient.main(): home narrowed...");

        Employee rec;
        Collection recs;
        Iterator iterator;
        Integer empNo;
        String empName;
        Float salary;
        int i;
      
       if ( (args.length > 0) && args[0].compareTo ("Create") == 0) {
	  // Create a new record and narrow the reference.//args[0] = CreateDBT, DropDBT, integer of employee no., Name, salary
	  empNo = new Integer(args[1]);
	  empName = new String(args[2]);
          salary = new Float(args[3]);
	  rec = (Employee) PortableRemoteObject.narrow(home.create(empNo, empName, salary), Employee.class);

	  System.out.println("EmployeeClient.main(): created " + empNo);

	} else if ( (args.length > 0) && args[0].compareTo ("List") == 0) {	  

	  recs = home.findAll();
	  iterator = recs.iterator();
	  i = 0;
	  while(iterator.hasNext()) {
	    rec = (Employee) PortableRemoteObject.narrow(iterator.next(), Employee.class);
	    System.out.println("row-" + i++ + " " + rec.getEmpNo() + " " + rec.getEmpName() + " " + rec.getSalary());
	  }
	  System.out.println("EmployeeClient.main(): found " + recs.size());
	} //end else

	
    } catch(NumberFormatException e) {
      System.err.println("NumberFormatException: " + e.getMessage());
    } catch(RemoteException e) {
      System.err.println("RemoteException: " + e.getMessage());
    } catch(IOException e) {
      System.err.println("IOException: " + e.getMessage());
    } catch(NamingException e) {
      System.err.println("NamingException: " + e.getMessage());
    } catch(CreateException e) {
      System.err.println("CreateException: " + e.getMessage());
    } catch(FinderException e) {
      System.err.println("FinderException: " + e.getMessage());
    }
*/
  }
}
