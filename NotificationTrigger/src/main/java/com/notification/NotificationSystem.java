package com.notification;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.cli.Options;
import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.ParseException;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import com.notification.db.*;


public class NotificationSystem {

	private static Logger log = Logger.getLogger(NotificationSystem.class);
	public static void main(String args[]) throws ParseException {
		
		Properties prop = new Properties();
		Options options = new Options();
		options.addOption("c",true,"configFilePath");
		options.addOption("l",true,"logFilePath");
		CommandLineParser cmdParser = new BasicParser();
		CommandLine cmdLine = cmdParser.parse(options,args);
		String configFilePath = cmdLine.getOptionValue("c");
		String logFilePath = cmdLine.getOptionValue("l");
		try {
			log.debug("Application started successfully--");
			System.out.println("Application started successfully--");
			prop.load(new FileInputStream(configFilePath));
			PropertyConfigurator.configure(logFilePath);
			new CheckNotification().startProccess(prop);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(1);
			
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		try {
		
		//	new ScripDetail().initializeContractDetails(prop);
			
			} catch (Exception e) {
				log.error("Unable to Fetch the ContractFile: ",  e );
			}
		
		DBCon.getInstance().releasePool();
		
	}

}
