package eu.reply.whitehall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import eu.reply.whitehall.dto.OpenDocumentListDto;
import eu.reply.whitehall.dto.OpenDocumentMapper;
import eu.reply.whitehall.dto.OpenNodeListDto;
import eu.reply.whitehall.dto.OpenNodeMapper;
import eu.reply.whitehall.service.OpenDocumentService;
import eu.reply.whitehall.service.OpenNodeService;

@Controller
@RequestMapping("/home")
public class HomeController {
	
	@Autowired
	private OpenDocumentService service;
	
	@RequestMapping(value="/{login}")
	public String getHomePage(@PathVariable(value = "login") String login) {
		return "home";
	}

	@RequestMapping(value="/records")
	public @ResponseBody OpenDocumentListDto getOpenDocuments() {
		
		OpenDocumentListDto openDocListDto = new OpenDocumentListDto();
		openDocListDto.setOpenDocuments(OpenDocumentMapper.map(service.readAll()));
		return openDocListDto;
	}
	

	@RequestMapping(value="/quit", method=RequestMethod.POST)
	public @ResponseBody Boolean quit() {
		System.err.println("size: " + service.readAll().size());
		return service.quit();
	}
	

}