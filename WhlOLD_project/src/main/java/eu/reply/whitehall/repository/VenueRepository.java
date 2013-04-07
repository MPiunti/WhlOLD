package eu.reply.whitehall.repository;

import org.springframework.data.neo4j.repository.GraphRepository;
import eu.reply.whitehall.domain.nodes.Venue;


public interface VenueRepository extends GraphRepository<Venue>  {
	
}
