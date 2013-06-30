package eu.reply.whitehall.controller;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import eu.reply.whitehall.domain.nodes.OpenDocument;
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
	
	@RequestMapping(value="/")
	public ModelAndView getOpenNodesPage(OpenDocument doc) {
		ModelAndView mav = new ModelAndView("open");
		mav.addObject("doc", doc);
		return mav;
	}
	
	@RequestMapping(value="/headers/{doc}")
	public @ResponseBody OpenNodeListDto getOpenHeaders(@PathVariable("doc") String doc_uk) {		

		OpenNodeListDto openNodeListDto = new OpenNodeListDto();
		//openNodeListDto.setOpenNodes(OpenNodeMapper.map(service.findAllHeaders(doc_name)));
		openNodeListDto.setOpenNodes(OpenNodeMapper.map(service.getHeaders(doc_uk)));
		return openNodeListDto;
	}

	/**
	 * List of Values for Datatables
	 * @param doc_uk
	 * @return
	 */
	@RequestMapping(value="/records/{doc}")
	public @ResponseBody OpenNodeListDto getOpenNodes(@PathVariable("doc") String doc_uk) {
		
		OpenNodeListDto openNodeListDto = new OpenNodeListDto();
		//openNodeListDto.setOpenNodes(OpenNodeMapper.map(service.findAllRecords(doc_name)));
		openNodeListDto.setOpenNodes(OpenNodeMapper.map(service.getRecords(doc_uk)));
		return openNodeListDto;
	}
	
	/**
	 * List of Values for Graph Visualization
	 * @param doc_uk
	 * @return
	 */
	@RequestMapping(value="/graph/{doc}")
	public @ResponseBody List<OpenNode> getOpenGraph(@PathVariable("doc") String doc_uk) {
		return service.getRecords(doc_uk);
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
