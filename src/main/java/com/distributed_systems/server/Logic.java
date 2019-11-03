package com.distributed_systems.server;

public class Logic {

	Database database;

	public Logic() {
		database = new Database();

		database.getConnection();
		database.createDatabase();
		database.createTable();

		// Empty the users to re-enter everyone
		database.deleteEveryone();

	}

	public String deposit(int id, int amount) {
		if (amount % 5 == 0) {
			
			int newBalance = amount + database.getMoney(id);
			database.deposit(id, newBalance);
			return "Deposit is done successfully!";
		} else {
			return "You have to insert paper bill only.";
		}
	}

	public String withdrawal(int id, int amount) {

		if (amount % 20 != 0 && amount % 50 != 0) {
			return "I'm sorry, but the amount must by devided by 20 or 50 paper bills";
		}

		int leftToWithdrawal = database.getLeftToWithdrawal(id);
		int money = database.getMoney(id);

		if (amount <= 0) {
			return "Please insert a valid amount of money";
		} else if (leftToWithdrawal > amount) {
			if (money > amount) {
				//Get as much as I want
				database.withdrawal(id, money - amount, leftToWithdrawal - amount);
				return "You were able to withdrawal " + amount;
			} else {
				//Get all my money (that left)
				database.withdrawal(id, 0, leftToWithdrawal - money);
				return "You were able to withdrawal " + money;
			}
		} else if (leftToWithdrawal <= amount) {
			if(money > amount) {
				database.withdrawal(id, money - leftToWithdrawal, 0);
				return "You were able to withdrawal " + leftToWithdrawal;
			} else if(money <= amount && money > leftToWithdrawal){
				database.withdrawal(id, money - leftToWithdrawal, 0);
				return "You were able to withdrawal " + leftToWithdrawal;
			} else {
				database.withdrawal(id, 0, leftToWithdrawal - money);
				return "You were able to withdrawal " + money;


			}
		}
		return null;

	}
	
	public String revertLeftToWithdrawal() {
		return database.revertLeftToWithdrawal();
	}

	public String addAcount(String name, int amount) {
		System.out.println("You are adding new customer to the database");

		return String.valueOf(database.addAccount(name, amount));
	}

	public String getLeftToWithdrawal(int id) {
		return "You're left to withdrawal amount is: " + database.getLeftToWithdrawal(id);
	}

}
