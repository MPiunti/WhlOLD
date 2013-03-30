package eu.reply.whitehall.repository;

import org.springframework.data.neo4j.repository.GraphRepository;


import eu.reply.whitehall.domain.nodes.OpenDocument;

public interface OpenDocumentRepository extends GraphRepository<OpenDocument>  {
	
	//OpenNode findByUniqueKey(String uniqueKey);
	
	
	/**
	   start document=node:__types__("className"="eu.reply.whitehall.domain.nodes.OpenDocument")
	    match document<-[:OWNS]-user
	    where user.login = {0} 
	    return document
	*/
	Iterable<OpenDocument> findByOwnersLogin(String Login);
}
