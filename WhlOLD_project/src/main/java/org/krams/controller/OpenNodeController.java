package org.krams.controller;

import org.krams.domain.OpenNode;
import org.krams.domain.Role;
import org.krams.domain.User;
import org.krams.dto.OpenNodeDto;
import org.krams.dto.OpenNodeListDto;
import org.krams.dto.OpenNodeMapper;
import org.krams.dto.UserDto;
import org.krams.dto.UserListDto;
import org.krams.dto.UserMapper;
import org.krams.service.OpenNodeService;
import org.krams.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/open")
public class OpenNodeController {

	@Autowired
	private OpenNodeService service;
	
	@RequestMapping(value="/{docname}")
	public ModelAndView getOpenNodesPage(@PathVariable("docname") String doc_name) {
		ModelAndView mav = new ModelAndView("open");
		mav.addObject("doc_name", doc_name);

		return mav;
	}
	
	@RequestMapping(value="/headers/{docname}")
	public @ResponseBody OpenNodeListDto getOpenHEaders(@PathVariable("docname") String doc_name) {		

		OpenNodeListDto openNodeListDto = new OpenNodeListDto();
		openNodeListDto.setOpenNodes(OpenNodeMapper.map(service.findAllHeaders(doc_name)));
		return openNodeListDto;
	}

		
	@RequestMapping(value="/records/{docname}")
	public @ResponseBody OpenNodeListDto getOpenNodes(@PathVariable("docname") String doc_name) {
		
		OpenNodeListDto openNodeListDto = new OpenNodeListDto();
		openNodeListDto.setOpenNodes(OpenNodeMapper.map(service.findAllRecords(doc_name)));
		return openNodeListDto;
	}
	
	
	
	@RequestMapping(value="/get")
	public @ResponseBody OpenNode get(@RequestBody OpenNode node) {
		return service.read(node);
	}

	@RequestMapping(value="/create", method=RequestMethod.POST)
	public @ResponseBody OpenNodeDto create(
			@RequestParam String name /*,
			@RequestParam String password,
			@RequestParam String firstName,
			@RequestParam String lastName,
			@RequestParam Integer role*/) {
		
		OpenNode newNode = new OpenNode(name);
		/*newNode.setName(name);
		newUser.setPassword(password);
		newUser.setFirstName(firstName);
		newUser.setLastName(lastName);
		newUser.setRole(newRole);*/
		
		return OpenNodeMapper.map(service.create(newNode));
	} 
	
	/*
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public @ResponseBody UserDto update(
			@RequestParam String username,
			@RequestParam String firstName,
			@RequestParam String lastName,
			@RequestParam Integer role) {

		Role existingRole = new Role();
		existingRole.setRole(role);
		
		User existingUser = new User();
		existingUser.setUsername(username);
		existingUser.setFirstName(firstName);
		existingUser.setLastName(lastName);
		existingUser.setRole(existingRole);
		
		return UserMapper.map(service.update(existingUser));
	}
	
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public @ResponseBody Boolean delete(
			@RequestParam String username) {

		User existingUser = new User();
		existingUser.setUsername(username);
		
		return service.delete(existingUser);
	}*/
	
	
	
	@RequestMapping(value="/quit", method=RequestMethod.POST)
	public @ResponseBody Boolean quit() {
		System.err.println("size: " + service.readAll().size());
		return service.quit();
	}
}
