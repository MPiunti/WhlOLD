package eu.reply.whitehall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import eu.reply.whitehall.repository.UserRepository;



@Controller
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	UserRepository userRepo;
	 
	/*@RequestMapping(value={"","/login", "/auth/logout"})
	public String getHomePage() {
		return "login";
	}*/
	 
	 
   @RequestMapping(value = {"","/login", "/auth/logout"}, method = RequestMethod.GET)
    public String login(@RequestParam(value = "login_error", required = false) boolean error, Model model) {
        //logger.debug("Received request to show login page, error "+error);
        if (error) {
            model.addAttribute("error", "You have entered an invalid username or password!");
        }
        return "login";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(
            @RequestParam(value = "j_username") String login,
            @RequestParam(value = "j_displayname") String name,
            @RequestParam(value = "j_password") String password,
            Model model) {

        try {
            userRepo.register(login,name,password);
            return "forward:/home/"+login;
        } catch(Exception e) {
            e.printStackTrace();
            model.addAttribute("j_username",login);
            model.addAttribute("j_displayname",name);
            model.addAttribute("error",e.getMessage());
            return "register";
        }
    }

    @RequestMapping(value = "/denied", method = RequestMethod.GET)
    public String denied() {
        //logger.debug("Received request to show denied page");
        return "/auth/deniedpage";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String registerPage() {
        //logger.debug("Received request to show register page");
        return "register";
    }

	
}