package eu.ase.rmiweb;

import java.rmi.*;
import java.rmi.registry.*;

public class SampleServerImageProgMain
{
  public static void main(String args[])
  {
    String ipregistry = "0.0.0.0";
    String portregistry = "1099";
    //set the security manager
    try
      {
        System.setSecurityManager(new RMISecurityManager());

        //create a local instance of the object
        SampleServerImageImpl Server = new SampleServerImageImpl();

        //put the local instance in the registry
        //Naming.rebind("SAMPLE-SERVER" , Server);
	Naming.rebind("rmi://"+ipregistry+":"+portregistry+"/SAMPLE-SERVER-IMGPROC", Server);

        System.out.println("Server for image processing waiting.....");
      }
    catch (java.net.MalformedURLException me)
      {
         System.out.println("Malformed URL: " + me.toString());
      }

    catch (RemoteException re)
      {
         System.out.println("Remote exception: " + re.toString());
      }

  }

}