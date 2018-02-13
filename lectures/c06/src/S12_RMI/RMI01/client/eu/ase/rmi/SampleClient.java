package eu.ase.rmi;

import java.rmi.*;
import java.rmi.server.*;

public class SampleClient  
{
   public static void main(String[]  args)
   {
      // set the security manager for the client
      System.setSecurityManager(new RMISecurityManager());
      //get the remote object from the registry
      try {
          System.out.println("Security Manager loaded");
          String url = "rmi://127.0.0.1:1099/SAMPLE-SERVER";
          SampleServerInterface remoteObject = (SampleServerInterface)Naming.lookup(url);
          System.out.println("Got remote object");
          //narrow the object down to a specific one
          //System.out.println("Location: " + System.getProperty("LOCATION"));
          // make the invocation
	  int result = remoteObject.sum(1,2);
          System.out.println(" 1 + 2 = " +  result);
      } catch (RemoteException exc) {
          System.out.println("Error in lookup: " + exc.toString());
      } catch (java.net.MalformedURLException exc) {
          System.out.println("Malformed URL: " + exc.toString());
      } catch (java.rmi.NotBoundException exc) {
          System.out.println("NotBound: " + exc.toString());
      }

   } //end method
} //end server
