package com.distributed_systems.ATMachine;

public class Logic {
	
	public void deposit(Database db, int id, int amount) {
		if(amount % 5 == 0) {
			db.deposit(id, amount);
			System.out.println("Deposit is done successfully!");
		} else {
			System.out.println("You have to insert paper bill only.");
		}
	}
	
	public void withdrawal(Database db, int id, int amount) {
		
		if(amount <= 0 ) {
			System.out.println("Please insert a valid amount of money");
			return;
		}
		
		if(amount % 20 != 0 && amount % 50 != 0) {
			System.out.println("I'm sorry, but the amount must by devided by 20 or 50 paper bills");
			return;
		}
		
		int leftToWithdrawal = db.getLeftToWithdrawal(id);
		
		
		if(leftToWithdrawal > amount) {
			db.withdrawal(id, amount, leftToWithdrawal);
			System.out.println("You were able to withdrawal " + amount);
		} else if(leftToWithdrawal <= amount) {
			db.withdrawal(id, leftToWithdrawal, leftToWithdrawal);
			System.out.println("You were able to withdrawal " + leftToWithdrawal);
			
			
		}
	}
	
}
