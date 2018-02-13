package eu.ase.rmiweb;

import java.rmi.*;

import java.io.*;
import java.awt.*;
import java.awt.image.*;
import javax.swing.*;
import javax.imageio.*;

public interface SampleServerImageInterface extends Remote
{
  public byte[] remoteProcessPictureTime(byte[] origPic, String xpoz, String ypoz, String w, String h, int counts) throws RemoteException;
}
