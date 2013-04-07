package eu.reply.whitehall.repository;

import org.springframework.data.neo4j.repository.GraphRepository;

import eu.reply.whitehall.domain.nodes.DBPediaLink;


public interface DBPediaLinkRepository extends GraphRepository<DBPediaLink>  {
	
}
