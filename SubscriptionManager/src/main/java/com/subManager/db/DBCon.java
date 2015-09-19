package com.subManager.db;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.apache.log4j.Logger;
import org.apache.tomcat.dbcp.dbcp.BasicDataSource;

import com.subManager.constant.AppConstant;



public class DBCon {
	private static DBCon instance = null;
	private InitialContext context;
	private BasicDataSource mysqlDataSource;
	private static Logger log = Logger.getLogger(DBCon.class);
	
	private DBCon() {
		try {
			context = new InitialContext();
			mysqlDataSource = (BasicDataSource) context.lookup("java:comp/env/jdbc/"+AppConstant.subMngDbNmSpace);
			
		} catch (NamingException e) {
			log.error("Naming Exception in DB : ", e);
		} catch (Exception e1) {
			log.error("SQL Exception",e1);
		}
	}
	public static DBCon getInstance() {
		if (instance == null) {
				instance = new DBCon();
		}
		return instance;
	}

	public Connection getConnection() throws SQLException {
		
		return mysqlDataSource.getConnection();
		
	}
	
	public void releasePool() {
		try {
			if (mysqlDataSource != null) {
				mysqlDataSource.close();
				log.debug("Closed mysqlDataSourceConnceton");
			}
		} catch (SQLException e) {
			log.error("SQL Exception in closing mysqlDataSource connction :", e);
		}
	
	}
	
	
}
