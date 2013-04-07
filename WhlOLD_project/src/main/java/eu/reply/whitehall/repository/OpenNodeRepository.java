package eu.reply.whitehall.repository;


import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;

import eu.reply.whitehall.domain.nodes.OpenNode;

public interface OpenNodeRepository extends GraphRepository<OpenNode>  {
	
	//OpenNode findByUniqueKey(String uniqueKey);
		
	 @Query("START n=node:docname(name = {0}) MATCH (n)-[:INCLUDES]->(x)-[r]->(d)  WHERE x.headerLine = 1 RETURN x")
	 Iterable<OpenNode> getHeaders(String doc_uk);
	
	 @Query("START n=node:docname(name={0}) MATCH (n)-[:INCLUDES]->(x)-[r]->(d)  WHERE x.headerLine = 0 RETURN x")
	 Iterable<OpenNode> getRecords(String doc_uk);
	
	
}
