package model;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;

public class ConnectionFactory {
	
		public Connection getConnection() {
			try {
				return (Connection) DriverManager.getConnection("jdbc:mysql://127.0.0.1/BDPROVAMAVEN","userprovamaven","123");
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		
	}
