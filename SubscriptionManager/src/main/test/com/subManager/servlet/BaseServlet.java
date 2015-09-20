package com.subManager.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.me.JSONException;
import org.json.me.JSONObject;





//import com.msf.mosl.servlet.BaseServlet;

/**
 * Servlet implementation class BaseServlet
 */

public abstract class BaseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(BaseServlet.class);  
    
    

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().println("GET Method Not Supported");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String Content_type = request.getContentType();
		
		JSONObject reqObj = new JSONObject();
		if (Content_type.equalsIgnoreCase("application/json")) {
			StringBuffer jb = new StringBuffer();
			try {
				BufferedReader reader = request.getReader();

				char[] chars = new char[4 * 1024];
				int len;
				while ((len = reader.read(chars)) >= 0) {
					jb.append(chars, 0, len);
				}

			} catch (Exception e) { 
				response.getWriter().println("Unable to Process the Request");
			}
			try {
				reqObj = new JSONObject( jb.toString() );
			} catch (JSONException e) {
				response.getWriter().println("Error in Request Body");
			}
		} else {
			response.getWriter().println("Invalid Content-Type");
		}
		JSONObject respObj = new JSONObject();
		
		String svcGrp = this.getSVCGroup();
		String svcName = this.getSVCName();
		String svcVer = this.getSVCVersion();
		
		logRequest(reqObj, svcGrp, svcName, svcVer);
		onProcess(reqObj, respObj, request, response);
		
		sendResponse(respObj, response, request);
		
		logResponse(respObj, svcGrp, svcName, svcVer);
	}
	
	

	

	private void logResponse(JSONObject respObj, String svcGrp, String svcName,
			String svcVer) {
		log.debug("Response Send: "+svcGrp + "/"+ svcName + "/"+svcVer+" " + 
			respObj.toString());
		
	}

	private void logRequest(JSONObject reqObj, String svcGrp, String svcName,
			String svcVer) {
		log.debug("Request Received: "+svcGrp + "/"+ svcName + "/"+svcVer+" " + 
				reqObj.toString());
		
	}

	private void sendResponse(JSONObject respObj,
			HttpServletResponse response, HttpServletRequest request) {
		String responseStr = respObj.toString();
		// log.debug("Response:"+responseStr);
		response.setContentType("application/json; charset=UTF-8");
		try {
			response.getWriter().println(responseStr);
			

		} catch (IOException e) {
			log.error("Error in sendData", e);
		}

	}
	
	protected abstract String getSVCGroup();
	protected abstract String getSVCName();
	protected abstract String getSVCVersion();
	protected abstract void onProcess( JSONObject reqObj, JSONObject respObj, HttpServletRequest request,
			HttpServletResponse response);

}
