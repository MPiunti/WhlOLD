package eu.reply.whitehall.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import org.springframework.data.neo4j.template.Neo4jOperations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import eu.reply.whitehall.domain.nodes.User;
import eu.reply.whitehall.opendata.security.OpenUserDetails;
import eu.reply.whitehall.opendata.security.OpenUserDetailsService;
import eu.reply.whitehall.repository.UserRepository;
import eu.reply.whitehall.repository.UserRepositoryImpl;

@Service
public class UserService extends UserRepositoryImpl {

	
	@Autowired
	private UserRepository userRepository;


	public User getUserFromSession(){
		return userRepository.getUserFromSession();
	}
	
	/**
	 * quit all the nodes Users
*/
	public boolean quit() {	
		userRepository.deleteAll();
		return true;
	} 
}	
