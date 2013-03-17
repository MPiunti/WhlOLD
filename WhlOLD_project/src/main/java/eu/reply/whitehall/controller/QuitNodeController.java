package eu.reply.whitehall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import eu.reply.whitehall.service.CSVService;


@Controller
@RequestMapping("/quit")
public class QuitNodeController {
	
	  @Autowired
	  private CSVService csvService;
	  
	  
	/**
	 * Drop All (Nodes and Relationships)
	 */
	@RequestMapping(value="/all", method = RequestMethod.GET)
	public String quit() {	
		csvService.cleanDb();
		return "loigin";
	}

}
