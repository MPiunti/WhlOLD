package eu.reply.whitehall.repository;

import org.springframework.data.neo4j.repository.GraphRepository;


import eu.reply.whitehall.domain.nodes.OpenDocument;

public interface OpenDocumentRepository extends GraphRepository<OpenDocument>  {
	
	//OpenNode findByUniqueKey(String uniqueKey);
}
