package vn.edu.iuh.fit.ktpm_tuan_07_20106801_phamthanhson.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class SendMail {
    @Autowired
    private JavaMailSender mailSender;

    public void sendMail(String toEmail, String subject, String text) {
        SimpleMailMessage message =new SimpleMailMessage();
        message.setFrom("sonpham28052002@gmail.com");
        message.setTo(toEmail);
        message.setText(text);
        message.setSubject(subject);
        mailSender.send(message);
        System.out.println("send to "+toEmail+" successfully");
    }
}
