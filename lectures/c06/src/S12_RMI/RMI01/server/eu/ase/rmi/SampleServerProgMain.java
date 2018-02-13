package eu.ase.rmi;

import java.rmi.*;
import java.rmi.registry.*;

public class SampleServerProgMain
{
  public static void main(String args[])
  {
    //set the security manager
    try {
        System.setSecurityManager(new RMISecurityManager());

        //create a local instance of the object
        SampleServerImpl Server = new SampleServerImpl();

        //put the local instance in the registry
        //Naming.rebind("SAMPLE-SERVER" , Server);
	Naming.rebind("rmi://127.0.0.1:1099/SAMPLE-SERVER", Server);

        System.out.println("Server waiting.....");
    } catch (java.net.MalformedURLException me) {
         System.out.println("Malformed URL: " + me.toString());
    } catch (RemoteException re) {
         System.out.println("Remote exception: " + re.toString());
    }

  } //end method

} //end class
