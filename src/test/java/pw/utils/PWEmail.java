package pw.utils;

import java.io.File;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.Multipart;

import pw.base.PWBaseTest;

/**
 * Author Tapan, Feb 26
 * 
 */
public class PWEmail extends PWBaseTest {
	public static boolean SendEmail() {
		String executionRunTime = PWBaseTest.mapAllVariables.get("executionRunTime");
		String reportName = PWBaseTest.mapAllVariables.get("reportName");
		String deviceName = PWBaseTest.mapAllVariables.get("deviceName");
		String jenEnv = PWBaseTest.mapAllVariables.get("testenv");
		String reportFolderName = PWBaseTest.mapAllVariables.get("reportFolderName");
		String suiteName = PWBaseTest.mapAllVariables.get("suiteName");
		String strEmailTo = PWBaseTest.mapAllVariables.get("emailto");
		String strEmailCC = PWBaseTest.mapAllVariables.get("emailcc");
		String emailfrom = PWBaseTest.mapAllVariables.get("emailfrom");
		String emailpass = PWBaseTest.mapAllVariables.get("emailpass");
		
		String sStatus = "Passed";
		if (PWLog.getTotalFail() > 0) {
			sStatus = "Failed";
		} else if (PWLog.getTotalPass() == 0 && PWLog.getTotalFail() == 0) {
			sStatus = "NotRun";
		}
		String sRepFldNam = "RunID-";
		String repTime = executionRunTime;
		if (reportName.contains("Mobile")) {
			deviceName = "Mobile";
		}
		String repNam = reportName + "-" + deviceName + " - " + jenEnv.toUpperCase() + " ENV, Test suite has " + sStatus
				+ " - " + repTime;
		String to = strEmailTo;
		String cc = strEmailCC;
		String subject = repNam;
		String content = "";
		content = "Hi,\r\n\n" + " Below is the test plan result.\r\n" + " \r\n"
				+ " \r---------------------------------------------------------------\n";
		content = content + "\rTotal Pass : " + PWLog.getTotalPass() + " Total Fail : " + PWLog.getTotalFail() + " Total TC : "
				+ (PWLog.getTotalPass() + PWLog.getTotalFail());
		content = content + "\r---------------------------------------------------------------\n";
//		String strPrjNam = "Minop/";
//		if (reportFolderName.contains("Megh")) {
//			strPrjNam = "Megh/";
//		}
//		content = content + "\rRefer Report links: http://172.18.105.101/" + strPrjNam + sRepFldNam + repTime + "-"
//				+ suiteName + " , for further details.\n";
		String signature = "\nRegards,\nQC Team";
		final String senderEmail = emailfrom;
		final String appPassword = emailpass;
		// Receiver's email
		String emailTo = to;//
		String emailCC = cc;
		// SMTP Server Properties
		Properties properties = new Properties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", "smtp.zoho.com");
		properties.put("mail.smtp.port", "587");
		// Create a new Session with Authentication
		Session session = Session.getInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(senderEmail, appPassword);
			}
		});
		try {
			// Create a new email message
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(senderEmail));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailTo));
			message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(emailCC));
			message.setSubject(subject);
			// message.setText(content + "\n\n" + signature);
			//
			// Attachment file path
			// Create the text part
			MimeBodyPart textPart = new MimeBodyPart();
			textPart.setText(content + "\n\n" + signature);
			// Create the attachment part
			String filePath = "data/addmaster.properties";
			File file = new File(filePath);
			// Combine parts
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(textPart);
			if (file.exists()) {
				MimeBodyPart attachmentPart = new MimeBodyPart();
				attachmentPart.attachFile(filePath);
				multipart.addBodyPart(attachmentPart);
			}
			// Set to message
			message.setContent(multipart);
			// Send the email
			Transport.send(message);
			System.out.println("✅ Email sent successfully!");
		} catch (Exception e) {
			System.out.println(e.getMessage().toString());
			return false;
		}
		return true;
	}
}
/// EOCLASS