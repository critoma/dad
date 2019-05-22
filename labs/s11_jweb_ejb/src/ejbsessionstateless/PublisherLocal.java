package ejbsessionstateless;

import javax.ejb.Local;
import java.rmi.RemoteException;

@Local
public interface PublisherLocal {
	public void publishNews() throws RemoteException;
}
