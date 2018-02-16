package eu.ase.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface AddVectInterface extends Remote {
	public int[] addVectors(int[] x, int[] y) throws RemoteException;
}
