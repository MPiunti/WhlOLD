package org.krams.domain;

import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;

@NodeEntity
public class OpenDocument {
	
	@GraphId
	private Long id;
	@Indexed
	private String name;	
	@Indexed
	private Long user_id;
	
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
	 * @return the user_id
	 */
	public Long getUser_id() {
		return user_id;
	}

	/**
	 * @param user_id the user_id to set
	 */
	public void setUser_id(Long user_id) {
		this.user_id = user_id;
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
