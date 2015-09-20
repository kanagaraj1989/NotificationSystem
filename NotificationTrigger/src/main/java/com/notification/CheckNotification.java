package com.notification;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.json.me.JSONObject;

public class CheckNotification {
	private static Logger log = Logger.getLogger(CheckNotification.class);
	
	public void startProccess(Properties prop)
	{
		while(true)
		{
			try {
				List<JSONObject> newNotification= new ArrayList<JSONObject>();
				new NewNotificationChecker().getNewNotification(prop, newNotification);
				List<JSONObject> subList = new ArrayList<JSONObject>();
				new SubscriptionList().getSubscriptionList(prop,newNotification ,subList);
				
				for(JSONObject tmpObj : subList)
				{
					log.debug("Mail Sender called=="+ tmpObj);
				}
				Thread.sleep(4000);
				
				subList.clear();
				newNotification.clear();
			} catch (Exception e) {
				log.error("Exception in CheckNotification System",e);
			}
		}
	}

}
