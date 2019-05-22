package ejbsessionstateless;

import java.rmi.RemoteException;

import javax.ejb.Remote;

@Remote
public interface PublisherRemote {
	public void publishNews() throws RemoteException;
}
