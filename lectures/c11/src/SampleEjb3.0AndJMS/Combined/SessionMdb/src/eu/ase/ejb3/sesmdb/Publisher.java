package eu.ase.ejb3.sesmdb;

import javax.ejb.*;
import java.rmi.RemoteException;

/**
 * Remote interface for Publisher enterprise bean. Declares one business method.
 */
public interface Publisher {
    void publishNews() throws RemoteException;
}
