package com.subManager.service;

import com.subManager.constant.AppConstant;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.me.JSONException;
import org.json.me.JSONObject;


import com.subManager.servlet.BaseServlet;

/**
 * Servlet implementation class SubscriptionUpdater
 */
@WebServlet("/SubscriptionUpdater")
public class SubscriptionUpdater extends BaseServlet {
	
	private static final long serialVersionUID = 1L;
    private static final String svcGrp = "Subscription";
    private static final String svcName = "Updater";
    private static final String svcVer = "1.0.0";
    
    private static Logger log = Logger.getLogger(BaseServlet.class);  
    
    private static final String REQ_FAIL = "Request Failed";
    private static final String ERROR_STATUS = "-1";
    private static final String SUCCESS_STATUS = "1";
    private static final String SUCCESS = "Success";
	@Override
	
	protected String getSVCGroup() {
		
		return svcGrp;
	}

	@Override
	protected String getSVCName() {
		return svcName;
	}

	@Override
	protected String getSVCVersion() {
		return svcVer;
	}

	@Override
	protected void onProcess(JSONObject reqObj, JSONObject respObj,
			HttpServletRequest request, HttpServletResponse response) {
		log.debug("called subscription updater--");
		
		try
		{
			parse(reqObj);
			respObj.put(AppConstant.STATUS,"1" );
			respObj.put(AppConstant.MESSAGE, SUCCESS);
		}catch(Exception ex)
		{
			log.error("level-1: SubScription Updated Error", ex);
			try{
			respObj.put(AppConstant.STATUS,ERROR_STATUS);
			respObj.put(AppConstant.MESSAGE, REQ_FAIL);
			}catch(Exception e)
			{
				log.error("level-2 : SubScription Updated Error",e);
			}
		}
	}
	
	private void parse( JSONObject reqObj) throws JSONException, SQLException
	{
		String userID = reqObj.getString("usrID");
		String emailID = reqObj.getString("emailID");
		String auther = reqObj.getString("auther");
		String publisher = reqObj.getString("publisher");
		String date1 = reqObj.getString("date1");
		String date2 = reqObj.getString("date2");
		String date_cond = reqObj.getString("date_cond");
		String price1 = reqObj.getString("price1");
		String price2 = reqObj.getString("price2");
		String price_cond = reqObj.getString("price_cond");
		String title = reqObj.getString("title");

		Connection connection = null; 
		CallableStatement callStm = null;
		
		try {
		connection = com.subManager.db.DBCon.getInstance().getConnection();
		
		callStm = connection.prepareCall("{call subscription_insert(?,?,?,?,?,?,?,?,?,?,?)}");
		callStm.setString(1,auther);
		callStm.setString(2,price1);
		callStm.setString(3,price2);
		callStm.setString(4, price_cond);
		callStm.setString(5,date1);
		callStm.setString(6,date2);
		callStm.setString(7, date_cond);
		callStm.setString(8, userID);
		callStm.setString(9, emailID);
		callStm.setString(10, publisher);
		callStm.setString(11, title);
		
		log.debug("storedProcedure:"+callStm.toString());
		
		callStm.executeUpdate();
		
		}finally
		{
			callStm.close();
			connection.close();
		}

	}

}
