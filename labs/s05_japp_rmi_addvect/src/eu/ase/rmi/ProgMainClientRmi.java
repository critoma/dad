package eu.ase.rmi;

import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;

public class ProgMainClientRmi {

	public static void main(String[]  args)
	   {
	      // set the security manager for the client
	      System.setSecurityManager(new RMISecurityManager());
	      //get the remote object from the registry
	      try {
	          System.out.println("Security Manager loaded");
	          String url = "rmi://127.0.0.1:1099/SAMPLE-SERVER-ADDV";
	          AddVectInterface remoteObject = (AddVectInterface)Naming.lookup(url);
	          System.out.println("Got remote object");
	          //narrow the object down to a specific one
	          //System.out.println("Location: " + System.getProperty("LOCATION"));
	          // make the invocation	
	          int[] x = new int[] {1, 2, 3};
	          int[] y = new int[] {4, 5, 6};
	          int[] result = remoteObject.addVectors(x, y);
	          
	          for(int i = 0; i < result.length; i++)
	        	  System.out.println(" r[" + i + "]=" +  result[i]);
	      } catch (RemoteException exc) {
	          System.out.println("Error in lookup: " + exc.toString());
	      } catch (java.net.MalformedURLException exc) {
	          System.out.println("Malformed URL: " + exc.toString());
	      } catch (java.rmi.NotBoundException exc) {
	          System.out.println("NotBound: " + exc.toString());
	      }

	   } //end method

}
