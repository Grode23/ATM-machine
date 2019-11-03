package com.distributed_systems.server;

public class Logic {
	
	Database database;
	
	public Logic() {
		database = new Database();
				
		database.getConnection();
		database.createDatabase();
		database.createTable();

	}
	
	public String deposit(int id, int amount) {
		if(amount % 5 == 0) {
			database.deposit(id, amount);
			return "Deposit is done successfully!";
		} else {
			return "You have to insert paper bill only.";
		}
	}
	
	public String withdrawal(int id, int amount) {	
		
		if(amount % 20 != 0 && amount % 50 != 0) {
			return "I'm sorry, but the amount must by devided by 20 or 50 paper bills";
		}
		
		int leftToWithdrawal = database.getLeftToWithdrawal(id);
		
		if(amount <= 0 ) {
			return "Please insert a valid amount of money";
		} else if(leftToWithdrawal > amount) {
			database.withdrawal(id, amount, leftToWithdrawal);
			return "You were able to withdrawal " + amount;
		} else if(leftToWithdrawal <= amount) {
			database.withdrawal(id, leftToWithdrawal, leftToWithdrawal);
			return "You were able to withdrawal " + leftToWithdrawal;
		}
		return null;
		
		
	}
	
}
