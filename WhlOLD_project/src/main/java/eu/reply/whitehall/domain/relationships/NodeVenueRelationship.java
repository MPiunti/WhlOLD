package eu.reply.whitehall.domain.relationships;

import org.springframework.data.neo4j.annotation.EndNode;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.RelationshipEntity;
import org.springframework.data.neo4j.annotation.StartNode;


import eu.reply.whitehall.domain.nodes.OpenNode;
import eu.reply.whitehall.domain.nodes.Venue;

@RelationshipEntity(type="LOCATED")
public class NodeVenueRelationship {
	
	@GraphId
	private Long id;
	
	@StartNode OpenNode openNode;
	@EndNode Venue venue;
	String link;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public OpenNode getOpenNode() {
		return openNode;
	}
	public void setOpenNode(OpenNode openNode) {
		this.openNode = openNode;
	}
	public Venue getVenue() {
		return venue;
	}
	public void setVenue(Venue venue) {
		this.venue = venue;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}

}
