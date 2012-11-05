package org.krams.repository;

import org.krams.domain.User;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.neo4j.repository.RelationshipOperationsRepository;

import eu.reply.whitehall.opendata.security.OpenUserDetailsService;

public interface UserRepository extends GraphRepository<User>,  
							RelationshipOperationsRepository<User>,
							OpenUserDetailsService {	
	
	//User findByUsername(String username);
	User findByLogin(String login);
}
