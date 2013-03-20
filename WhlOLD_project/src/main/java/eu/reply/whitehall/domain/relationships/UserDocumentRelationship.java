package eu.reply.whitehall.domain.relationships;

import org.springframework.data.neo4j.annotation.EndNode;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.RelationshipEntity;
import org.springframework.data.neo4j.annotation.StartNode;

import eu.reply.whitehall.domain.nodes.OpenDocument;
import eu.reply.whitehall.domain.nodes.User;

@RelationshipEntity(type="OWNS")
public class UserDocumentRelationship {
	
	@GraphId
	Long id;
	
	@StartNode User user;
	@EndNode OpenDocument openDocument;
	String ownership;
	
	public UserDocumentRelationship(){}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public OpenDocument getOpenDocument() {
		return openDocument;
	}
	public void setOpenDocument(OpenDocument openDocument) {
		this.openDocument = openDocument;
	}
	public String getOwnership() {
		return ownership;
	}
	public void setOwnership(String ownership) {
		this.ownership = ownership;
	}


}
