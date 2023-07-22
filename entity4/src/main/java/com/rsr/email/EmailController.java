package com.rsr.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/email")
public class EmailController {

	@Autowired
	EmailService emailService;

	@GetMapping(path="/send")
	void send(@RequestParam("to") String to,
			  @RequestParam("subject") String subject,
			  @RequestParam("text") String text)
	{
		emailService.sendEmail(to, subject, text);
	}
	
}
