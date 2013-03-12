package eu.reply.whitehall.repository;

import org.springframework.data.neo4j.repository.GraphRepository;

import eu.reply.whitehall.domain.OpenNode;

public interface OpenNodeRepository extends GraphRepository<OpenNode>  {
	//OpenNode findByUniqueKey(String uniqueKey);
}
