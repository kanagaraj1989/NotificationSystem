package com.notification;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.json.me.JSONException;
import org.json.me.JSONObject;
import com.notification.db.*;

public class NewNotificationChecker {
	
	private static Logger log = Logger.getLogger(NewNotificationChecker.class);
	
	public void getNewNotification(Properties prop, 
			List<JSONObject> newNotification) throws SQLException, JSONException
	{
		Statement st = null;
		ResultSet rs = null;
	    DBCon.getInstance().setProperties(prop);
		Connection connection = null;
		 
		  try {
		  connection  = DBCon.getInstance().getConnection();
		  String querry = "CALL getNewNotification();";
		  st =  connection.createStatement();
		  rs = st.executeQuery(querry);
		 
		  log.debug("==NewNotificationChecker :"+querry.toString());
		  parseResultSet(prop, rs, newNotification);
		  }catch(Exception ex)
		  {
			  System.out.println("Exception Occured=="+ ex);  
			  log.debug("Exception occured ==", ex);
		  
		  } finally {
			  rs.close();
			  st.close();
			  connection.close();
		  }
		
	}

	private void parseResultSet(Properties prop, ResultSet rs, 
			List<JSONObject> newNotification) throws SQLException, JSONException {
		
		if(!rs.isBeforeFirst()) {
			 log.debug("No Data Available "); 
			 return;
		  } else {
			  while(rs.next()) {
				  JSONObject notiObj = new JSONObject();
				  notiObj.put("title", rs.getString("tittle"));
				  notiObj.put("auther", rs.getString("auther"));
				  notiObj.put("publisher", rs.getString("publisher"));
				  notiObj.put("release_date", rs.getDate("release_date").toString());
				  notiObj.put("list_price",rs.getString("list_price"));
				  notiObj.put("id", rs.getString("id"));
				  newNotification.add(notiObj);
				  invalidate_record(rs.getString("id"), prop);
				  log.debug("==New Notification Record="+ notiObj.toString());
		  }
		
	}
	}
 
	private void invalidate_record(String notiID, 
			Properties prop) throws SQLException
	{
		Statement st = null;
	    DBCon.getInstance().setProperties(prop);
		Connection connection = null;
		 
		  try {
			  connection = DBCon.getInstance().getConnection();
		  String querry = "call invalidateRecord("+ notiID+")";
				
		  st =  connection.createStatement();
		  st.executeUpdate(querry);
		 
		  log.debug("Invalidate Record :"+querry.toString());
		  
		  }catch(Exception ex)
		  {
			  System.out.println("Exception Occured=="+ ex);  
			  log.debug("Exception occured ==", ex);
		  
		  } finally {
			  st.close();
			  connection.close();
		  }
	}

}
