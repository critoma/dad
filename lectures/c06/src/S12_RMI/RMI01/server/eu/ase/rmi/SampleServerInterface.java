package eu.ase.rmi;

import java.rmi.*;

public interface SampleServerInterface extends Remote
{
  public int sum(int a, int b) throws RemoteException;
}
