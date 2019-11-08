

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
	
	public int getLeftToWithdrawal(int id) {
		String command = "SELECT leftToWithdrawal FROM customers WHERE id=?";
		ResultSet rs = null;
		
		try {
			PreparedStatement prep = conn.prepareStatement(command);
			prep.setInt(1, id);
			rs = prep.executeQuery();
			rs.next();
			
			return rs.getInt("leftToWithdrawal");
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		return 0;
		
	}
	
	public int getMoney(int id) {
		String command = "SELECT money FROM customers WHERE id=?";
		ResultSet rs = null;
		
		try {
			PreparedStatement prep = conn.prepareStatement(command);
			prep.setInt(1, id);
			rs = prep.executeQuery();
			rs.next();
			
			return rs.getInt(1);
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		return 0;
		
	}
	
	
	
	public void setLeftToWithdrawal(int leftToWithdrawal, int id) {
		String command = "UPDATE customers SET leftToWithdrawal=? WHERE id=?;";
		
		try {
			PreparedStatement prep = conn.prepareStatement(command);
			prep.setInt(1, leftToWithdrawal);
			prep.setInt(2, id);
			prep.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	public String revertLeftToWithdrawal() {
		String command = "UPDATE customers SET leftToWithdrawal=1000;";
		
		try {
			Statement statement = conn.createStatement();
			statement.execute(command);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return "LeftToWithdrawal is reverted to all customers";
	}
	
	public void checkAccount(int id) {
		String customer = "SELECT name, money, leftToWithdrawal FROM customers WHERE id=?;";
		
		try {
			PreparedStatement prep = conn.prepareStatement(customer);
			prep.setInt(1, id);
			prep.executeQuery();
			//I HAVE TO RETURN THE RESULTS
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	public void deposit(int id, int money) {
		String customer = "UPDATE customers SET money=? WHERE id=?;";
		
		try {
			PreparedStatement prep = conn.prepareStatement(customer);
			prep.setInt(1, money);
			prep.setInt(2, id);
			prep.execute();
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
	
	public int addAccount(String name, int amount) {
		String command = "INSERT INTO customers (name, money) VALUES (?, ?);";
		
		try {
			PreparedStatement prep = conn.prepareStatement(command);
			prep.setString(1, name);
			prep.setInt(2, amount);
			prep.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return getId();
		
	}
	
	public void deleteEveryone() {
		String command = "DELETE FROM customers;";
		
		try {
			Statement statement = conn.createStatement();
			statement.execute(command);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	private int getId() {
		
		String query = "SELECT MAX(id) FROM customers;";
		
		Statement statement;
		ResultSet result = null;
		int id = 0;
		try {
			statement = conn.createStatement();
			result = statement.executeQuery(query);
			
			result.next();
			id = result.getInt(1);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return id;
	}



}