

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteLogic extends Remote {
	
	public String deposit(int id, int amount) throws RemoteException;
	public String withdrawal(int id, int amount)throws RemoteException;
	public String revertLeftToWithdrawal()throws RemoteException;
	public int addAcount(String name, int amount)throws RemoteException;
	public String getLeftToWithdrawal(int id)throws RemoteException;

}
