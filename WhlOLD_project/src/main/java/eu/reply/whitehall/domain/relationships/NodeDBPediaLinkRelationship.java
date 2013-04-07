package eu.reply.whitehall.domain.relationships;

import org.springframework.data.neo4j.annotation.EndNode;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.RelationshipEntity;
import org.springframework.data.neo4j.annotation.StartNode;


import eu.reply.whitehall.domain.nodes.DBPediaLink;
import eu.reply.whitehall.domain.nodes.OpenNode;
import eu.reply.whitehall.domain.nodes.Venue;

@RelationshipEntity(type="DBP_LINKED")
public class NodeDBPediaLinkRelationship {
	
	@GraphId
	private Long id;
	
	@StartNode OpenNode openNode;;
	@EndNode DBPediaLink dBPediaLink;
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
	public DBPediaLink getdBPediaLink() {
		return dBPediaLink;
	}
	public void setdBPediaLink(DBPediaLink dBPediaLink) {
		this.dBPediaLink = dBPediaLink;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	

}
