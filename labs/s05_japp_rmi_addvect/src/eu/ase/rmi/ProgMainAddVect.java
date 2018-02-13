package eu.ase.rmi;

import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;

public class ProgMainAddVect {

	public static void main(String args[])
	  {
		// before running this project start RMI Name Service - rmiregistry application:
		// in terminal run - PLUS be sure after compiling phase to set the call for rmic utility program:
		// sudo su
		// pass = stud
		// export JAVA_HOME=/opt/software/java/jdks/jdk1.6.0_35
		// export PATH=$JAVA_HOME/bin:/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin:/usr/games
		// export CLASSPATH=.:$JAVA_HOME/jre/lib/rt.jar
		// cd /home/stud/dad/labs/s08_rmi_addvect/bin
		// rmic -classpath . -v1.2 -keep eu.ase.rmi.AddVectImpl
		// rmiregistry
		// PLUS include in Eclipse run configuration the policy.all file:
		// ProgMainAddVectRMIServer_Config => -Djava.security.policy=policy.all
		// ProgMainAddVectRMIClient_Config => -Djava.security.policy=policy.all
		// 

	    //set the security manager
	    try {
	        System.setSecurityManager(new RMISecurityManager());

	        //create a local instance of the object
	        AddVectImpl Server = new AddVectImpl();

	        //put the local instance in the registry
	        //Naming.rebind("SAMPLE-SERVER" , Server);
	        Naming.rebind("rmi://127.0.0.1:1099/SAMPLE-SERVER-ADDV", Server);

	        System.out.println("Server waiting.....");
	    } catch (java.net.MalformedURLException me) {
	         System.out.println("Malformed URL: " + me.toString());
	    } catch (RemoteException re) {
	         System.out.println("Remote exception: " + re.toString());
	    }

	  } //end method

}
