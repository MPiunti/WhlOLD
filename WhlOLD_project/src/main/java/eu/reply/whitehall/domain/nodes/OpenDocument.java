package eu.reply.whitehall.domain.nodes;

import java.util.Set;

import org.neo4j.graphdb.Direction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

@NodeEntity
public class OpenDocument {
	
	@GraphId
	private Long id;
	@Indexed
	private String name;	
	

	@RelatedTo(type = "OWNS", direction = Direction.INCOMING)
    Set<User> owners;

	@RelatedTo(type = "INCLUDES", direction = Direction.OUTGOING)
    Set<OpenNode> nodes;
	
	@Indexed
	private int visible;	 // 0 is not visible, 1 is visible
	
	
	
	public OpenDocument(){}

	public OpenDocument(String name){
		this.name= name;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}


	/**
	 * @return the visible
	 */
	public int getVisible() {
		return visible;
	}

	/**
	 * @param visible the visible to set
	 */
	public void setVisible(int visible) {
		this.visible = visible;
	}

	
}
