package progmmo.server.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import progmmo.server.config.EmailConfig;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Component("emailSender")
public class EmailSender {

    @Autowired
    private EmailConfig eConf;

    public void sendEmail(String recipient, String subject, String body){
        Properties prop = System.getProperties();

        prop.put("mail.smtp.host", eConf.getHost());
        prop.put("mail.smtp.port", "25");
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.auth", "true");

        Session session = Session.getInstance(prop, new javax.mail.Authenticator(){
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(eConf.getAddress(), eConf.getPassword());
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(eConf.getAddress()));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));

            message.setSubject(subject);
            message.setText(body);
            Transport.send(message);
        } catch (Exception e){ e.printStackTrace(); };
    }

}
