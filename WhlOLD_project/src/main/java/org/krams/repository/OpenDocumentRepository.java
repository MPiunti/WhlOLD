package org.krams.repository;

import org.krams.domain.OpenDocument;
import org.springframework.data.neo4j.repository.GraphRepository;

public interface OpenDocumentRepository extends GraphRepository<OpenDocument>  {
	//OpenNode findByUniqueKey(String uniqueKey);
}
