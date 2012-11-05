package org.krams.controller;

import org.krams.dto.OpenDocumentListDto;
import org.krams.dto.OpenDocumentMapper;
import org.krams.dto.OpenNodeListDto;
import org.krams.dto.OpenNodeMapper;
import org.krams.service.OpenDocumentService;
import org.krams.service.OpenNodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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