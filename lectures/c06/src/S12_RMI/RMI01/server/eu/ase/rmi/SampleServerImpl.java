package eu.ase.rmi;

import java.rmi.*;
import java.rmi.server.*;

public class SampleServerImpl extends UnicastRemoteObject 
   implements SampleServerInterface
{
  SampleServerImpl() throws RemoteException
  {
     super();
  }

  public int sum(int a,int b) throws RemoteException
  {
     return a + b;
  }
}


