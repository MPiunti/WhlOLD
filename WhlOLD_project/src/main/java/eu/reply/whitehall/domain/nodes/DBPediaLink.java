package eu.reply.whitehall.domain.nodes;

import java.util.Set;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;


@NodeEntity
public class DBPediaLink {
	
   @GraphId 
   Long id;

   @Indexed 
   String URI; 
   
   String Description; 
   
   @Fetch @RelatedTo(type = "DBP_LINKED", direction = Direction.INCOMING)
   Set<OpenNode> nodes;
   
   private String type = "DBPEDIA_URI";
   
   public DBPediaLink(){}
   
   public DBPediaLink(String uri){
	   this.URI = uri;
   }

   public Long getId() {
	return id;
   }
	
   public void setId(Long id) {
	this.id = id;
   }
	
	public String getURI() {
		return URI;
	}
	
	public void setURI(String uRI) {
		URI = uRI;
	}
	
	public Set<OpenNode> getNodes() {
		return nodes;
	}
	
	public void setNodes(Set<OpenNode> nodes) {
		this.nodes = nodes;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}	
	   
  
}
