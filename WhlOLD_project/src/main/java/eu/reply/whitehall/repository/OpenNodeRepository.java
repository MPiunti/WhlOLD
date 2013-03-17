package eu.reply.whitehall.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.neo4j.template.Neo4jOperations;

import eu.reply.whitehall.domain.nodes.OpenNode;

public interface OpenNodeRepository extends GraphRepository<OpenNode>  {
	
	//OpenNode findByUniqueKey(String uniqueKey);
}
