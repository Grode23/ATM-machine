import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Random;

public class Client {

	private static final String HOST = "localhost";
	private static final int PORT = Registry.REGISTRY_PORT; // 1099

	public static void main(String[] args) {

		try {
			// Locate RMI registry
			Registry registry = LocateRegistry.getRegistry(HOST, PORT);

			// Look up for a specific name and get remote reference (stub)			
			String rmiObjectName = "RemoteLogic";
			RemoteLogic ref = (RemoteLogic)registry.lookup(rmiObjectName);

			//Add acount with a default name and budget for every new customer, because I'm bored
			int id = ref.addAcount("MyName", 800);
			System.out.println("My ID is: " + id);
			
			Random random = new Random();

			// Do this forever
			// Request and get an answer
			while (true) {

				try {
					// Sleep for some time
					// I don't want to write or delete something all the time
					Thread.sleep(random.nextInt(2500) + 500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				//
				int randomNum = random.nextInt(3) + 1;
				int amount = (random.nextInt(200) + 1) * 5;

				// Delete, write or edit
				switch (randomNum) {
				case 1:
					System.out.println(ref.deposit(id, amount));
					break;
				case 2:
					System.out.println(ref.withdrawal(id, amount));
					break;
				case 3:
					System.out.println(ref.getLeftToWithdrawal(id));
					break;
				default:
					System.out.println("Something else is called");
					break;
				}

			}

		} catch (NotBoundException e) {
			System.out.println("Here");
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}

	}

}
