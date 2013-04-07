package eu.reply.whitehall.domain.nodes;

import java.util.LinkedList;
import java.util.Set;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

@NodeEntity
public class OpenNode {
	
	@GraphId
	private Long id;
	
	@Indexed(indexName = "unique") 
	private String unique;
	
	@Indexed
	private String name;
	
	private String type = "OPEN_NODE";
	
	@Indexed
	private int headerLine;
	
	private LinkedList<String> row;
	
	@RelatedTo(type = "INCUDES", direction = Direction.INCOMING)
    Set<OpenDocument> documents;
	
	@Fetch @RelatedTo(type = "LOCATED", direction = Direction.OUTGOING)
    Set<Venue> venues;
	
	@Fetch @RelatedTo(type = "DBP_LINKED", direction = Direction.OUTGOING)
    Set<DBPediaLink> dBPediaLinks;

	
	
	public OpenNode(){}

	public OpenNode(String unique){
		this.unique= unique;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the headers
	 */
	public int getHeaderLine() {
		return headerLine;
	}

	/**
	 * @param headers the headers to set
	 */
	public void setHeaderLine(int headers) {
		this.headerLine = headers;
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

	public Set<OpenDocument> getDocuments() {
		return documents;
	}

	public void setDocuments(Set<OpenDocument> documents) {
		this.documents = documents;
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
