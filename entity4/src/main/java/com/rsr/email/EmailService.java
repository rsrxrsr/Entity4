package com.rsr.email;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
//import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import java.io.File;
//import java.io.IOException;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender javaMailSender;
	@Value("${file.uploadFolder}")
	private String UPLOAD_FOLDER;

	public void sendEmail(String to, String subject, String text) {

        SimpleMailMessage msg = new SimpleMailMessage();
        /*
        msg.setTo("correoTriple@gmail.com"); // ("to_1@gmail.com", "to_2@gmail.com", "to_3@yahoo.com")
        msg.setSubject("Testing from Spring Boot");
        msg.setText("Hello World \n Spring Boot Email");
		*/
        msg.setTo(to);
        msg.setSubject(subject);
        msg.setText(text);
        // 
        javaMailSender.send(msg);

    }

	public void sendEmailWithAttachment() {

		try {
	        MimeMessage msg = javaMailSender.createMimeMessage();
	        MimeMessageHelper helper;
			helper = new MimeMessageHelper(msg, true); // true = multipart message

	        helper.setTo("correoTriple@gmail.com");
	        helper.setSubject("Testing from Spring Boot");
	        helper.setText("<h1>Check attachment for image!</h1>", true); // true = text/html; default = text/plain
	
	        String pathFile=UPLOAD_FOLDER+"Recibo-Jul.pdf";
	        FileSystemResource resource = new FileSystemResource(new File(pathFile));
	        helper.addAttachment("my_file.pdf", resource);
            //helper.addAttachment("my_file.pdf", new ClassPathResource(pathFile));
	
	        javaMailSender.send(msg);
		} catch (MessagingException e) {
			e.printStackTrace();
		}  

    }
	
}
