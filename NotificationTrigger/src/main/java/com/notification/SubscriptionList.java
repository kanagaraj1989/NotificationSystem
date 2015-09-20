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

import com.notification.db.DBCon;

public class SubscriptionList {
	private static Logger log = Logger.getLogger(SubscriptionList.class);
	
	public void getSubscriptionList(Properties prop, List<JSONObject> notiList, 
			List<JSONObject> subscrList) throws JSONException, SQLException
	{
		for(JSONObject subObj : notiList)
		{
			log.debug("");
			String auther = subObj.getString("auther");
			String publisher = subObj.getString("publisher");
			String price = subObj.getString("list_price");
			String date = subObj.getString("release_date");
			String title = subObj.getString("title");
			
			
			Statement st = null;
			ResultSet rs = null;
		    DBCon.getInstance().setProperties(prop);
			Connection connection = null;
			 
			  try {
				  
			  connection = DBCon.getInstance().getConnection();
			  String querry = "CALL getSubscriptionList('"+auther+"','" +
					  			publisher+"',"+ price + ",'"+date+"','" +
					  			title+"');";
			  st =  connection.createStatement();
			  rs = st.executeQuery(querry);
			 
			  log.debug("GetSubscription Query :"+querry.toString());
			  parseResultSet( prop, rs, subObj, subscrList);
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
	}

	private void parseResultSet( Properties prop , ResultSet rs, JSONObject subObj,
			List<JSONObject> subscrList) throws SQLException, JSONException {
		
		if(!rs.isBeforeFirst()) {
			 log.debug("No Data Available to NSECM"); 
			 return;
		  } else {
			  while(rs.next()) {
				  //id, userID, emailID
			//	  String subID = "";
				  JSONObject notiObj = new JSONObject(subObj.toString());
				  notiObj.put("userID", rs.getString("userID"));
				  notiObj.put("emailID", rs.getString("emailID"));
			
				  String notiID = subObj.getString("id");
				  SendMailSubscribe.sendEmail(rs.getString("emailID"), "Alert", notiObj.toString());
				  invalidate_record( notiID, prop);
				  subscrList.add(notiObj);
				  log.debug("Subscription final Record="+ notiObj.toString());
				  
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
