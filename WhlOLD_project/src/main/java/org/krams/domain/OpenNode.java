package org.krams.domain;

import java.util.LinkedList;

import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;

@NodeEntity
public class OpenNode {
	
	@GraphId
	private Long id;
	
	private String name;
	
	private LinkedList<String> row;
	
	public OpenNode(String name){
		this.name=name;
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
	 * @return the row
	 */
	public LinkedList<String> getRow() {
		return row;
	}

	/**
	 * @param row the row to set
	 */
	public void setRow(LinkedList<String> row) {
		this.row = row;
	}

}
