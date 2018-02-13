import java.rmi.*;

public interface SampleServer extends Remote
{
  public int sum(int a,int b)      throws RemoteException;
}
