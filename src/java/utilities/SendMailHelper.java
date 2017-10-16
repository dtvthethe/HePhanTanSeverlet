package utilities;

import Configs.Config;
import Bean.Email;
import java.io.File;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * Created by Admin on 10/14/2017.
 */
public class SendMailHelper {

    private Properties props;
    private Session session;

    public SendMailHelper() {
        props = new Properties();
        ConfigEmail();

        session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(Config.EMAIL, Config.PASSWORD);
            }
        });

    }

    private void ConfigEmail() {
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
    }

    public void SendMailToFindDevice(Email emi) {
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(Config.EMAIL));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emi.getEmailAddress()));
            
            message.setSubject(emi.getSubject(),"UTF-8");

            MimeBodyPart messageBodyPart = new MimeBodyPart();
            Multipart multipart = new MimeMultipart();
            messageBodyPart = new MimeBodyPart();
            File att = new File(new File(emi.getPathFolderAttachImage()), emi.getFileNameAttachment());
            messageBodyPart.attachFile(att);
            DataSource source = new FileDataSource(att);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName("YourDeviceLocation.jpg");

            BodyPart htmlBodyPart = new MimeBodyPart();
            htmlBodyPart.setContent(emi.getBody(), "text/html; charset=UTF-8");

            multipart.addBodyPart(htmlBodyPart);
            multipart.addBodyPart(messageBodyPart);
            message.setContent(multipart);
            
            System.out.println("Sending");
            Transport.send(message);
            System.out.println("Done");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
