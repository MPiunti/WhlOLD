package org.krams.controller;

import org.krams.dto.OpenDocumentListDto;
import org.krams.dto.OpenDocumentMapper;
import org.krams.dto.OpenNodeListDto;
import org.krams.dto.OpenNodeMapper;
import org.krams.service.OpenDocumentService;
import org.krams.service.OpenNodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/")
public class HomeController {
	
	@Autowired
	private OpenDocumentService service;

	@RequestMapping
	public String getHomePage() {
		return "redirect:/home";
	}
	
	@RequestMapping(value="home/records")
	public @ResponseBody OpenDocumentListDto getOpenDocuments() {
		
		OpenDocumentListDto openDocListDto = new OpenDocumentListDto();
		openDocListDto.setOpenDocuments(OpenDocumentMapper.map(service.readAll()));
		return openDocListDto;
	}
}