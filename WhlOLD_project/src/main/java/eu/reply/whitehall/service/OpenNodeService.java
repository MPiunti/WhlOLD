package eu.reply.whitehall.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.conversion.EndResult;
import org.springframework.stereotype.Service;

import eu.reply.whitehall.domain.nodes.OpenNode;
import eu.reply.whitehall.domain.nodes.User;
import eu.reply.whitehall.repository.OpenNodeRepository;
import eu.reply.whitehall.repository.RoleRepository;
import eu.reply.whitehall.repository.UserRepository;

@Service
public class OpenNodeService {

	@Autowired
	private OpenNodeRepository openNodeRepository;
	
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
	
	/**
	 * 
	 * @param docName
	 * @return
	 */
	public List<OpenNode> findAllHeaders(String docName) {
		List<OpenNode> openNodes = new ArrayList<OpenNode>();
		
		//String query = "START n=node:nodes(name = \""+docName+"\") WHERE (n.headerLine = 0) RETURN n";
		String query = "START n=node:nodes(name:"+docName+") WHERE (n.headerLine = 1) RETURN n";

		EndResult<OpenNode> results = openNodeRepository.findAllByQuery("n",query); 
		for (OpenNode r: results) { 
			openNodes.add(r);
		}				
		System.err.println(" headers found: " + openNodes.size() );
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
		String query = "START n=node:nodes(name:"+docName+") WHERE (n.headerLine = 0) RETURN n";

		EndResult<OpenNode> results = openNodeRepository.findAllByQuery("n",query); 
		for (OpenNode r: results) { 
			openNodes.add(r);
		}				
		System.err.println(" data  found: " + openNodes.size() + " records" );
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
}
