package org.krams.dto;

import java.io.Serializable;
import java.util.LinkedList;

public class OpenNodeDto implements Serializable {
	
	private Long id;
	private String name, unique;
	private LinkedList<String> row;

	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the unique
	 */
	public String getUnique() {
		return unique;
	}
	/**
	 * @param unique the unique to set
	 */
	public void setUnique(String unique) {
		this.unique = unique;
	}
	public String getName() {
		return name;
	}
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
