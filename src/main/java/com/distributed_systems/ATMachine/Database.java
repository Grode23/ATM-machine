package com.distributed_systems.ATMachine;

import java.sql.*;

public class Database {

	private Connection conn;

	private final String jdbcURL = "jdbc:mysql://localhost:3306?userTimezone=true&serverTimezone=UTC";

	// Connect to the database
	public Connection getConnection() {

		try {
			conn = DriverManager.getConnection(jdbcURL, "default", "password");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return conn;
	}

	// Does what it says
	public void createDatabase() {
		String createDatabase = "CREATE DATABASE IF NOT EXISTS ATM;";
		String useDatabase = "USE ATM";
		try {
			Statement statement = conn.createStatement();
			statement.executeUpdate(createDatabase);
			statement.executeUpdate(useDatabase);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Does what it says
	public void createTable() {

		//Table with all customers
		String customers = "CREATE TABLE IF NOT EXISTS customers(id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(20), money INT, leftToWithdrawal INT DEFAULT 1000)";

		try {
			Statement statement = conn.createStatement();
			statement.executeUpdate(customers);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	// Insert a new customer in the database with default value of leftToWithdrawal as 1000
	public void insertMember(String name, int money) {
		String command = "INSERT INTO customers(name, money) VALUES(?, ?);";

		try {
			PreparedStatement prep = conn.prepareStatement(command);
			prep.setString(1, name);
			prep.setInt(2, money);
			prep.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	public void withdrawal(int id, int money, int moneyLeft) {
		
		String customer = "UPDATE customers SET leftToWithdrawal=?, money=? WHERE id=?;";
		
		try {
			PreparedStatement prep = conn.prepareStatement(customer);
			prep.setInt(1, moneyLeft);
			prep.setInt(2, money);
			prep.setInt(3, id);
			prep.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}


}