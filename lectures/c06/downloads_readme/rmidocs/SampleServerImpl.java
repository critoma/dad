//-----------------------------------------------------------
// File: SampleServerImpl.java
//-----------------------------------------------------------

import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.*;

public class SampleServerImpl extends UnicastRemoteObject
                             implements SampleServer
{
  SampleServerImpl() throws RemoteException
  {
     super();
  }

  public int sum(int a,int b) throws RemoteException
  {
     return a + b;
  }

  public static void main(String args[])
  {
    //set the security manager
    try
      {
        System.setSecurityManager(new RMISecurityManager());

        //create a local instance of the object
        SampleServerImpl Server = new SampleServerImpl();

        //put the local instance in the registry
        Naming.rebind("SAMPLE-SERVER" , Server);

        System.out.println("Server waiting.....");
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


