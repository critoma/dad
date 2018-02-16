package eu.ase.rmi;

import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;

public class AddVectImpl extends UnicastRemoteObject implements AddVectInterface {
	
	private static final long serialVersionUID = 1L;

	public AddVectImpl() throws RemoteException {
		super();
	}
	
	public int[] addVectors(int[] x, int[] y) throws java.rmi.RemoteException {
		int[] result = null;
		if(x != null && y != null && x.length == y.length) {
			result = new int[x.length];
			for(int i = 0; i < x.length; i++) {
				result[i] = x[i] + y[i];
			}
		} 
		return result;
	}
}