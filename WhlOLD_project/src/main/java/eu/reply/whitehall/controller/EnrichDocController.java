package eu.reply.whitehall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import eu.reply.whitehall.service.OpenNodeService;

@Controller
@RequestMapping("/enrich")
public class EnrichDocController {
	
	@Autowired
	private OpenNodeService service;
	
	@RequestMapping(value="/geo/{doc}")
	public @ResponseBody String geoLocate(@PathVariable("doc") String doc_uk) {
		service.getGeoCode(doc_uk, 2);
		return doc_uk;
	}
	
	@RequestMapping(value="/dbpedia/{doc}")
	public  @ResponseBody String linkDBPedia(@PathVariable("doc") String doc_uk) {
		service.getDBPediaLookUp(doc_uk, 0);
		//System.out.println(dbpedia);
		return doc_uk;
	}
	
	@RequestMapping(value="/alchemy/{doc}")
	public  @ResponseBody String linkAlchemy(@PathVariable("doc") String doc_uk) {
		service.getAlchemyDisambiguation(doc_uk, 0);
		//System.out.println(dbpedia);
		return doc_uk;
	}

}
