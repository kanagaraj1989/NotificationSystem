package com.notification;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

public class SendMailSubscribe {

	private static Logger log = Logger.getLogger(SendMailSubscribe.class);
	public static void sendEmail(String to, String attribute, String attr_value) {
		final String username = "siva44s@yahoo.in";
		final String password = "*****";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.mail.yahoo.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				});

		try {
			String hai = "";// hai kaku, you are in Amazon hindle hack";

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("sivakaku@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(to));
			message.setSubject("Kindle subscription !!!");
			message.setText("Hi Subcriber" + "\n\n your subscription to "
					+ attr_value + " has been updated for " + attribute
					+ " \n \nRegards \nCodeBeautify");

			Transport.send(message);

			System.out.println("Done");
			
			log.debug("Mail Send to: "+ to+ " Msg: " + message.toString() );
			/*
			 * Kanagaraj K <kanagaraj.20jan@gmail.com>, Gopalakishnan R
			 * <togopalakrishnan@gmail.com>, kathir s
			 * <kathir1611.rayz@gmail.com>, siva rasu <sivakaku@gmail.com>
			 */

		}

		catch (MessagingException e) {
			// throw new RuntimeException(e);
			e.printStackTrace();
			System.out
					.println("Username or Password are incorrect ... exiting !");
		}
	}
}

