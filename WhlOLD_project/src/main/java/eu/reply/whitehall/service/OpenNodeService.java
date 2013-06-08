package eu.reply.whitehall.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.conversion.EndResult;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.stereotype.Service;

import eu.reply.whitehall.client.dbpedia.DBpediaLookUpClient;
import eu.reply.whitehall.client.geocode.GoogleGeoCodeClient;
import eu.reply.whitehall.domain.nodes.DBPediaLink;
import eu.reply.whitehall.domain.nodes.OpenNode;
import eu.reply.whitehall.domain.nodes.Venue;
import eu.reply.whitehall.domain.relationships.NodeDBPediaLinkRelationship;
import eu.reply.whitehall.domain.relationships.NodeVenueRelationship;
import eu.reply.whitehall.repository.OpenNodeRepository;
import eu.reply.whitehall.repository.VenueRepository;
import eu.reply.whitehall.repository.DBPediaLinkRepository;

@Service
public class OpenNodeService {
	
	@Autowired 
	private Neo4jTemplate template;

	@Autowired
	private OpenNodeRepository openNodeRepository;
	
	@Autowired
	private VenueRepository venueRepository;
	
	@Autowired
	private DBPediaLinkRepository dBPediaLinkRepository;
	
	
	@Autowired
	private DBpediaLookUpClient dbPediaLookUpClient;
	@Autowired
	private GoogleGeoCodeClient googleGeoCodeClient;
	
	
	
	public OpenNode create(OpenNode openNode) {
		//OpenNode existingNode = openNodeRepository.findByUniqueKey(openNode.getName());
		
		/*if (existingNode != null) {
			throw new RuntimeException("Record already exists!");
		}*/
		
		//user.getRole().setUser(user);
		return openNodeRepository.save(openNode);
	}
	
	public OpenNode read(OpenNode od) {
		return od;
	}
	
	public List<OpenNode> readAll() {
		List<OpenNode> openNodes = new ArrayList<OpenNode>();
		
		EndResult<OpenNode> results = openNodeRepository.findAll();
		for (OpenNode r: results) {
			openNodes.add(r);
		}
		
		return openNodes;
	}
	
	/**
	 * 
	 * @param pName
	 * @param obj
	 * @return
	 */	
	public List<OpenNode> findAllbyPropertyValue(String pName, Object obj) {
		List<OpenNode> openNodes = new ArrayList<OpenNode>();
		
		EndResult<OpenNode> results = openNodeRepository.findAllByPropertyValue(pName,obj);
		for (OpenNode r: results) {
			openNodes.add(r);
		}		
		return openNodes;
	}
	
	public List<OpenNode> getHeaders(String doc_uk) {
		List<OpenNode> openNodes = new ArrayList<OpenNode>();
		Iterable<OpenNode> results = openNodeRepository.getHeaders(doc_uk);
		for (OpenNode r: results) { 
			openNodes.add(r);
		}				
		System.err.println("HEADERS found: " + openNodes.size() );
		return openNodes;
	}
	
	public List<OpenNode> getRecords(String doc_uk) {
		List<OpenNode> openNodes = new ArrayList<OpenNode>();
		Iterable<OpenNode> results = openNodeRepository.getRecords(doc_uk);
		//OpenNode no = results.iterator().next();
		for (OpenNode r: results) { 
			//template.fetch(r);
			template.fetch(r.getVenues());
			openNodes.add(r);			
			/*if(r.getdBPediaLinks().size()>0)
				System.err.println("DBLINK:" + r.getdBPediaLinks().iterator().next().getURI() );*/
		}				
		System.err.println("CONTENTS found: " + openNodes.size() );
		return openNodes;
	}
	
	
	// ####################### Alternative Solution: InBound Queries #################################################
	
	/**
	 * 
	 * @param docName
	 * @return
	 */
	public List<OpenNode> findAllHeaders(String docName) {
		List<OpenNode> openNodes = new ArrayList<OpenNode>();
		
		//String query = "START n=node:nodes(name = \""+docName+"\") WHERE (n.headerLine = 0) RETURN n";
		//String query = "START n=node:nodes(name:"+docName+") WHERE (n.headerLine = 1) RETURN n";
		String query = "START n=node:OpenDocument(name={0}) MATCH (n)-[:INCLUDES]->(x)  WHERE x.headerLine = 1 RETURN x";

		EndResult<OpenNode> results = openNodeRepository.findAllByQuery(docName,query); 
		for (OpenNode r: results) { 
			openNodes.add(r);
		}				
		System.err.println("QUERY:" +query+"\n headers found: " + openNodes.size() );
		return openNodes;
	}
	
	/**
	 * 
	 * @param docName
	 * @return
	 */
	public List<OpenNode> findAllRecords(String docName) {
		List<OpenNode> openNodes = new ArrayList<OpenNode>();
		
		//String query = "START n=node:nodes(name = \""+docName+"\") WHERE (n.headerLine = 0) RETURN n";
		//String query = "START n=node:nodes(name:"+docName+") WHERE (n.headerLine = 0) RETURN n";
		String query = "START n=node:OpenDocument(name={0}) MATCH (n)-[:INCLUDES]->(x)  WHERE x.headerLine = 0 RETURN x";

		EndResult<OpenNode> results = openNodeRepository.findAllByQuery(docName,query); 
		for (OpenNode r: results) { 
			openNodes.add(r);
		}				
		System.err.println("QUERY:" +query+"\n data  found: " + openNodes.size() + " records" );
		return openNodes;
	}
	
	/*
	public User update(User user) {
		User existingUser = userRepository.findByUsername(user.getUsername());
		
		if (existingUser == null) {
			return null;
		}
		
		existingUser.setFirstName(user.getFirstName());
		existingUser.setLastName(user.getLastName());
		existingUser.getRole().setRole(user.getRole().getRole());
		
		roleRepository.save(existingUser.getRole());
		return userRepository.save(existingUser);
	}
	
	public Boolean delete(User user) {
		User existingUser = userRepository.findByUsername(user.getUsername());
		
		if (existingUser == null) {
			return false;
		}
		
		userRepository.delete(existingUser);
		return true;
	}
	
	/**
	 * quit all the nodes
	 */
	public boolean quit() {	
		openNodeRepository.deleteAll();
		return true;
	}
	
	
	/**
	 * Enrich Nodes with Goegraphic Coordinates
	 * @param doc_uk
	 * @return
	 */
	public void getDBPediaLookUp(String doc_uk, Integer ...col_id){

		Map<String,String> ret; String key="";
		List<OpenNode> records = getRecords(doc_uk);
		for(OpenNode node:records){
			key=node.getRow().get(col_id[0]); //+","+ node.getRow().get(1) +","+ node.getRow().get(2);
			try{
				key=node.getRow().get(0); //+","+ node.getRow().get(1) +","+ node.getRow().get(2);
				ret = dbPediaLookUpClient.linkDbPedia(key.replace(" ", "+"));				
				
				DBPediaLink dBLink = new DBPediaLink(ret.get("URI"));	
				dBLink.setDescription(ret.get("DESCR"));
				dBPediaLinkRepository.save(dBLink);
				
				/* Store Relationship */
	        	template.createRelationshipBetween(node, dBLink, NodeDBPediaLinkRelationship.class, "DBP_LINKED",false);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Enrich Nodes with Goegraphic Coordinates
	 * @param doc_uk
	 * @return
	 */
	public void getGeoCode(String doc_uk, Integer ...col_id){
		Map<String,String> ret; 
		String address="";
		List<OpenNode> records = getRecords(doc_uk);
		for(OpenNode node:records){
			try{
				address=node.getRow().get(col_id[0]); //+","+ node.getRow().get(1) +","+ node.getRow().get(2);
				ret = googleGeoCodeClient.geoCode(address.replace(" ", "+"));
				Venue venue = new Venue(new Float(ret.get("LON")), new Float(ret.get("LAT")));			
				venueRepository.save(venue);
				
				/* Store Relationship */
	        	template.createRelationshipBetween(node,venue, NodeVenueRelationship.class, "LOCATED",false);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}
