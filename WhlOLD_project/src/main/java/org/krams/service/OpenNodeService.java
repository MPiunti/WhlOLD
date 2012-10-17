package org.krams.service;

import java.util.ArrayList;
import java.util.List;

import org.krams.domain.OpenNode;
import org.krams.domain.User;
import org.krams.repository.OpenNodeRepository;
import org.krams.repository.RoleRepository;
import org.krams.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.conversion.EndResult;
import org.springframework.stereotype.Service;

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
