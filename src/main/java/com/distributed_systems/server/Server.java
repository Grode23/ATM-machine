package com.distributed_systems.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;


/**
 * Server is responsible for every connection between itself and the clients
 * Server handles the clients (all kinds). Gets the input and transform it as it needs
 * Every clients' output gets passed to board through here
 * Whenever board stuff is done, the input goes here and finally gets send back to the client
 * 
 * P.S. There is a lot of prints to show the current state of the process
 */
public class Server {

	//The one and only port
	public static final int PORT = 1234;

	private ServerSocket ss;
	private ArrayList<Thread> connections = new ArrayList<>();
	private Logic db = new Logic();
	private ReentrantLock lock = new ReentrantLock();


	public static void main(String[] args) {			
		new Server();
	}
	
	public Server() {
		System.out.println("Server is running");
		
		try {

			ss = new ServerSocket(PORT);

			while (true) {

				// Once per connection/client
				Socket socket = ss.accept();
				System.out.println("Client connected.");
				
				//Create and start a new connection with a client
				Connection conn = new Connection(socket);
				Thread thread = new Thread(conn);
				connections.add(thread);
				thread.start();

			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public class Connection implements Runnable {

		Socket socket;

		// Stream for giving and getting information
		DataInputStream input;
		DataOutputStream output;

		public Connection(Socket socket) {
			this.socket = socket;
			
		}

		@Override
		public void run() {
			

			try {
				input = new DataInputStream(socket.getInputStream());
				output = new DataOutputStream(socket.getOutputStream());

				while (true) {

					//Server sleeps if nothing is sent because otherwise it would drain big part of the CPU
					while (input.available() == 0) {
						Thread.sleep(5);
					}
					
					//If there is an input, go on
					if(!input.equals(null)) {
						
						lock.lock();
						
						int id = Integer.parseInt(input.readUTF());
						String action = input.readUTF();
						int amount = 0;
						if(action.equals("Deposit") || action.equals("Withdrawal")) {
							amount = Integer.parseInt(input.readUTF());
						}
						
						lock.unlock();
						
						handleClients(id, action, amount);
					
					} else {
						System.out.println("Client is gone.");
					}

				}

			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}

		/**
		 * Input is being read and gets sent wherever it has to
		 * With other words, server reads the first word of the message, 
		 * so it can understand what kind of request it is
		 * Finally, server calls the appropriate board method 
		 */
		private void handleClients(int id, String action, int amount) {		
				
			try {
				switch (action) {
				case "Deposit": //Deposit
					output.writeUTF(db.deposit(id, amount));
					output.flush();
					break;
				case "Withdrawal": //Withdrawal
					output.writeUTF(db.withdrawal(id, amount));
					output.flush();
					break;
				case "L": // How much money left for withdrawal today
					
					break;
				case "B": //For balance, how much money is in the account
					
					break;
				default:
					break;
				}
			}catch(IOException e) {
				e.printStackTrace();
			}
						
		}
		
	}

}
