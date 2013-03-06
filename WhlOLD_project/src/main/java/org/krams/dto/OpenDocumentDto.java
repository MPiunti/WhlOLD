package org.krams.dto;

import java.io.Serializable;


public class OpenDocumentDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id,user_id;
	private Integer visible;
	private String name;
	
	
	
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
	public Integer getVisible() {
		return visible;
	}
	/**
	 * @param visible the visible to set
	 */
	public void setVisible(Integer visible) {
		this.visible = visible;
	}

	
}
