package eu.ase.ejb3.stateful;

import javax.ejb.Remove;

public interface HTMLReader {
    public StringBuffer getContents(String friendlyName) throws HTTPResponseException;
    @Remove void checkout();
}
