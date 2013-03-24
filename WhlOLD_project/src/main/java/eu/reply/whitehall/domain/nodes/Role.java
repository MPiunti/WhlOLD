package eu.reply.whitehall.domain.nodes;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

@NodeEntity
public class Role {
	
	@GraphId
	private Long id;
	
	private String type = "ROLE";	
	
	@RelatedTo(type = "PLAYS", direction = Direction.INCOMING)
	private User user;
	
	private Integer role;
	
	public Role() {}
	
	public Role(Integer role) {
		this.role = role;
	}
	
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
	public Integer getRole() {
		return role;
	}
	public void setRole(Integer role) {
		this.role = role;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
