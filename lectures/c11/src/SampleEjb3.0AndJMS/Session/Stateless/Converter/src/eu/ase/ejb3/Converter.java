package eu.ase.ejb3;

import javax.ejb.EJBObject;
import java.rmi.RemoteException;
import java.math.*;


public interface Converter {
    public BigDecimal dollarToYen(BigDecimal dollars) throws RemoteException;

    public BigDecimal yenToEuro(BigDecimal yen) throws RemoteException;
}
