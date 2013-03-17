package eu.reply.whitehall.repository;

import org.springframework.data.neo4j.repository.GraphRepository;

import eu.reply.whitehall.domain.nodes.Role;

public interface RoleRepository extends GraphRepository<Role>  {
	
}
