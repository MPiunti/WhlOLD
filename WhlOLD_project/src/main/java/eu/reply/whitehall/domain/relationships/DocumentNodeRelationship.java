package eu.reply.whitehall.domain.relationships;

import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.EndNode;
import org.springframework.data.neo4j.annotation.RelationshipEntity;
import org.springframework.data.neo4j.annotation.StartNode;

import eu.reply.whitehall.domain.nodes.OpenDocument;
import eu.reply.whitehall.domain.nodes.OpenNode;


@RelationshipEntity(type="INCLUDES")
public class DocumentNodeRelationship {
	
	@GraphId
	private Long id;
	
	@StartNode OpenDocument openDocument;
	@EndNode OpenNode openNode;
	String link;
	
	public DocumentNodeRelationship(){}



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public OpenDocument getOpenDocument() {
		return openDocument;
	}

	public void setOpenDocument(OpenDocument openDocument) {
		this.openDocument = openDocument;
	}

	public OpenNode getOpenNode() {
		return openNode;
	}

	public void setOpenNode(OpenNode openNode) {
		this.openNode = openNode;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
	
	
}
