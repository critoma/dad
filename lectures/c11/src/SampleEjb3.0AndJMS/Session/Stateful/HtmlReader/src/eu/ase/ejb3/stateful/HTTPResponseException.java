package eu.ase.ejb3.stateful;

import java.net.*;
import java.io.*;

public class HTTPResponseException extends Exception {
    public HTTPResponseException() {
    }

    public HTTPResponseException(String msg) {
        super(msg);
    }
}
