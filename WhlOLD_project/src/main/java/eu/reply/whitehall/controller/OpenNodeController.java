package eu.reply.whitehall.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import eu.reply.whitehall.domain.nodes.OpenNode;
import eu.reply.whitehall.dto.OpenNodeDto;
import eu.reply.whitehall.dto.OpenNodeListDto;
import eu.reply.whitehall.dto.OpenNodeMapper;
import eu.reply.whitehall.service.OpenNodeService;

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
			@RequestParam String name ) {
		
		OpenNode newNode = new OpenNode(name);
		return OpenNodeMapper.map(service.create(newNode));
	} 
	

	
	@RequestMapping(value="/quit", method=RequestMethod.POST)
	public @ResponseBody Boolean quit() {
		System.err.println("size: " + service.readAll().size());
		return service.quit();
	}
}
