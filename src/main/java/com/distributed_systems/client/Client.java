package com.distributed_systems.client;

import static com.distributed_systems.server.Server.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

public class Client {
	
	private Random random = new Random();
	private ArrayList<Thread> connections = new ArrayList<>();	
	private Socket socket;
	private int id = 0;
	
	public static void main(String[] args) {
		new Client();
	}
	
	public Client() {

		try {
			// Connection with the server
			socket = new Socket("localhost", PORT);			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Connection conn = new Connection(socket);
		Thread thread = new Thread(conn);
		connections.add(thread);
		thread.start();

	}

	protected class Connection implements Runnable {

		// Stream for giving and getting information
		private DataInputStream input;
		protected DataOutputStream output;

		public Connection(Socket socket) {

			try {
				//Initialize streams
				input = new DataInputStream(socket.getInputStream());
				output = new DataOutputStream(socket.getOutputStream());

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void run() {
			
			//Get your ID
			try {
				id = Integer.parseInt(input.readUTF());
			} catch (NumberFormatException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			System.out.println(id);
			
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

				int randomNum = random.nextInt(3) +1;
				int amount = (random.nextInt(200)+1) * 5; 
				
				
				// Delete, write or edit
				switch (randomNum) {
				case 1:
					requestDeposit(amount);
					break;
				case 2:
					requestWithdrawal(amount);
					break;
				case 3:
					requestLeftToWithdrawal();
					break;
				default:
					System.out.println("Something else is called");
					break;
				}

				answerFromServer();
			}

		}
		
		private void requestLeftToWithdrawal() {
			try {
				output.writeUTF(String.valueOf(id));
				output.writeUTF("LeftToWithdrawal");
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		//Gets the response from the server
		protected void answerFromServer() {
			try {
				
				String line = input.readUTF();	
				System.out.println(line);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}		
		
		private void requestDeposit(int amount) {
			try {
				output.writeUTF(String.valueOf(id));
				output.writeUTF("Deposit");
				output.writeUTF(String.valueOf(amount));
				output.flush();

			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
		private void requestWithdrawal(int amount) {
			try {
				output.writeUTF(String.valueOf(id));
				output.writeUTF("Withdrawal");
				output.writeUTF(String.valueOf(amount));
				output.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}

	}

}
