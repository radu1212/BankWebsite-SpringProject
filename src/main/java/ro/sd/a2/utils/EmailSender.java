package ro.sd.a2.utils;

import org.crsh.console.jline.internal.Log;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ro.sd.a2.controller.RegisterController;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import javax.mail.Session;
import javax.mail.Transport;

@Service
public class EmailSender
{

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(RegisterController.class);


    /**
     * @param recepient - the recipient of the email
     * @param subject - the subject of the email
     * @param text - the content of the email
     * @throws Exception - message not sent
     */
    public static void sendMail(String recepient, String subject, String text) throws Exception {

        Log.info("Preparing to send email to " + recepient);
        Properties properties = new Properties();

        //Enable authentication
        properties.put("mail.smtp.auth", "true");
        //Set TLS encryption enabled
        properties.put("mail.smtp.starttls.enable", "true");
        //Set SMTP host
        properties.put("mail.smtp.host", "smtp.gmail.com");
        //Set smtp port
        properties.put("mail.smtp.port", "587");

        //Your gmail address
        String myAccountEmail = "siminaradu12@gmail.com";
        //Your gmail password
        String password = "becali12";

        //Create a session with account credentials
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myAccountEmail, password);
            }
        });

        //Prepare email message
        Message message = prepareMessage(session, myAccountEmail, recepient, subject, text);

        //Send mail
        assert message != null;
        Transport.send(message);
        Log.info("Message sent successfully to " + recepient);
    }

    /**
     * @param session - prepares a session within which to send the email
     * @param myAccountEmail - the senders email
     * @param recepient - the email address of the recipient
     * @param subject - the subject of the email
     * @param text - the content of the email
     * @return - a Message entity which is to be sent during the sendMail method
     */
    private static Message prepareMessage(Session session, String myAccountEmail, String recepient, String subject, String text) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
            message.setSubject(subject);
            String htmlCode = "<h1>" + text + "</h1> <br/>";
            message.setContent(htmlCode, "text/html");
            return message;
        } catch (Exception ex) {
            Logger.getLogger(EmailSender.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
} 