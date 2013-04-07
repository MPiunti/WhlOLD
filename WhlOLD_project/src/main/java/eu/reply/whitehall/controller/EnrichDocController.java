package eu.reply.whitehall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import eu.reply.whitehall.service.OpenNodeService;

@Controller
@RequestMapping("/enrich")
public class EnrichDocController {
	
	@Autowired
	private OpenNodeService service;
	
	@RequestMapping(value="/geo/{doc}")
	public void geoLocate(@PathVariable("doc") String doc_uk) {
		service.getGeoCode(doc_uk);
		//System.out.println(geo);
	}
	
	@RequestMapping(value="/dbpedia/{doc}")
	public void linkDBPedia(@PathVariable("doc") String doc_uk) {
		service.getDBPediaLookUp(doc_uk);
		//System.out.println(dbpedia);
	}

}
