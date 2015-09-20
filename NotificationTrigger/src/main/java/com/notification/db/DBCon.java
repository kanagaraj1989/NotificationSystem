package com.notification.db;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.tomcat.dbcp.dbcp.BasicDataSource;
public class DBCon {
	private static DBCon instance = null;
	
	public static final int MYSQL = 1;
	
	private static Logger log = Logger.getLogger(DBCon.class);
	
	private BasicDataSource pds = null;
	private Properties properties = null;
	
	private DBCon() {

	}
	public static DBCon getInstance() {
		if (instance == null) {
				instance = new DBCon();
		}
		return instance;
	}
	public void setProperties(final Properties properties) throws SQLException {

		getInstance().properties = properties;
        log.debug("Properties=="+properties.toString());
		pds = new BasicDataSource();
          
		// Setting connection properties of the data source
		pds.setDriverClassName(properties.getProperty("db.driver_class"));
		pds.setUrl(properties.getProperty("db.url"));
		pds.setUsername(properties.getProperty("db.username"));
		pds.setPassword(properties.getProperty("db.password"));
		// Setting pool properties
		pds.setInitialSize(Integer.parseInt(properties
				.getProperty("db.initial_pool_size")));
		pds.setMinIdle(Integer.parseInt(properties
				.getProperty("db.min_pool_size")));
		pds.setMaxIdle(Integer.parseInt(properties
				.getProperty("db.max_pool_size")));

	}
	public Connection getConnection() throws SQLException {
		log.debug("calling for getConnection\n");
		Connection connection = null;
	
		 connection =  pds.getConnection();
			log.debug("Openning mysqlDataSource connection :"
				+ System.identityHashCode(connection));
		
		 return connection;
		
			
		
	}


	public void releasePool() {
		try {
			if (pds != null) {
				pds.close();
				log.debug("closed MSSQL Contract Datasource");
			}
		} catch (SQLException e) {
			log.debug("SQL Exception MSSQL-->ContractFile DataSource");
		}
		
	}
	
	
}
