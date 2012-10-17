package org.krams.repository;

import org.krams.domain.OpenNode;
import org.springframework.data.neo4j.repository.GraphRepository;

public interface OpenNodeRepository extends GraphRepository<OpenNode>  {
	OpenNode findByUniqueKey(String uniqueKey);
}
