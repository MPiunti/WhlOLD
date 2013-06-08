package eu.reply.whitehall.dto;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Set;

import eu.reply.whitehall.domain.nodes.DBPediaLink;
import eu.reply.whitehall.domain.nodes.Venue;

public class OpenNodeDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String name, unique;
	private LinkedList<String> row;

	private Set<Venue> venues;
	
	private Set<DBPediaLink> dBPediaLinks;

	
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
	public Set<Venue> getVenues() {
		return venues;
	}
	public void setVenues(Set<Venue> venues) {
		this.venues = venues;
	}
	public Set<DBPediaLink> getdBPediaLinks() {
		return dBPediaLinks;
	}
	public void setdBPediaLinks(Set<DBPediaLink> dBPediaLinks) {
		this.dBPediaLinks = dBPediaLinks;
	}
	
}
