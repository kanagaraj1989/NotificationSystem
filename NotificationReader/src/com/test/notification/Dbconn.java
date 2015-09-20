package com.test.notification;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Dbconn {
	private static Connection connect = null;
//	private Statement statement = null;
//	private PreparedStatement preparedStatement = null;
//	private ResultSet resultSet = null;

	public void rec_insert( BookInfo bookinfo, String key)
			throws SQLException {

		String query = "{ call insertBookinfo(?,?,?,?,?,?) }";

		PreparedStatement pstmt = connect.prepareStatement(query);
		pstmt.setInt(1, Integer.parseInt(key));
		pstmt.setString(2, bookinfo.getTitle());
		pstmt.setString(3, bookinfo.getAuthors());
		pstmt.setString(5, bookinfo.getRelease_date());
		pstmt.setString(4, bookinfo.getPublisher());
		pstmt.setDouble(6, bookinfo.getList_price());

		int rowAffected = pstmt.executeUpdate();
		if (rowAffected == 1)
			System.out.println("done ");
		if (pstmt != null) {
			pstmt.close();
		}
	}

	
	
	public void readDataBase() throws Exception {
		try {
			// This will load the MySQL driver, each DB has its own driver
			Class.forName("com.mysql.jdbc.Driver");
			// Setup the connection with the DB
			connect = DriverManager
					.getConnection("jdbc:mysql://localhost/notification?"
							+ "user=root&password=root");

		} catch (Exception e) {
			throw e;
		}

	}

	// You need to close the resultSet
	public void close() {
		try {

			if (connect != null) {
				connect.close();
			}
		} catch (Exception e) {

		}
	}

}