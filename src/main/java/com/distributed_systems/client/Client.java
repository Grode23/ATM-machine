package com.distributed_systems.client;

import static com.distributed_systems.server.Server.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

/**
 * Client is an abstract class for Reader and Writer
 * It contains all the common stuff of them
 * Such as Inner class Connection, methods and objects
 */
public abstract class Client {
	
	private Random random = new Random();
	private ArrayList<Thread> connections = new ArrayList<>();	
	private Socket socket;
	private int id = 1;
	
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
			// Do this forever
			// Request and get an answer
			while (true) {

				try {
					// Sleep for some time
					// I don't want to write or delete something all the time
					Thread.sleep(random.nextInt(3000) + 2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				int randomNum = random.nextInt(2);
				
				
				// Delete, write or edit
				if (randomNum == 1) {
					System.out.println("Deposit is called.");
					requestDeposit(500);
				} else if (randomNum == 2) {
					System.out.println("Write is called.");
					requestWrite(pick);
				} else {
					System.out.println("Edit is called.");
					requestEdit(pick);
				}

				answerFromServer();
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
		
		private void requestDeposit(int money) {
			
		}

		private void requestDelete(int pick) {

			try {

				output.writeUTF(String.valueOf(id));
				output.writeUTF(action);
				output.flush();

				System.out.println("Request is sent.");
			} catch (IOException e1) {
				e1.printStackTrace();
			}

		}


	}

}
