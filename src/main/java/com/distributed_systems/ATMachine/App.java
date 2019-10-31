package com.distributed_systems.ATMachine;

public class App 
{
	public static void main(String[] args) {
		Database db = new Database();
		
		db.getConnection();
		db.createDatabase();
		db.createTable();
				
		db.insertMember("Kostas", 200);
		System.out.println(db.getLeftToWithdrawal(2));
		System.out.println("All done");
	}
}
