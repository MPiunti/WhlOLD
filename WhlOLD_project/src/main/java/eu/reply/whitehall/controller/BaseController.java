package eu.reply.whitehall.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping("/")
public class BaseController {

	
	@RequestMapping
	public String getHomePage() {
		System.out.println("Michele Start");
		return "login";
	}

}