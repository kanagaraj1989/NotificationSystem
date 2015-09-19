package com.subManager.service;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
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
		
		
	}

}
