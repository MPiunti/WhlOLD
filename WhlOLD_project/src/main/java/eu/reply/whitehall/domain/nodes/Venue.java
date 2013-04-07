package eu.reply.whitehall.domain.nodes;

import java.util.Set;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;
import org.springframework.data.neo4j.support.index.IndexType;

@NodeEntity
public class Venue {
	
   @GraphId 
   Long id;
	
   String name;
   @Indexed(indexName = "VenueLocation") String wkt;
   
   @Fetch @RelatedTo(type = "LOCATED", direction = Direction.INCOMING)
   Set<OpenNode> nodes;
   
   private String type = "LOCATION";	
   
   public  Venue() {

   }
   
   public  Venue(float lon, float lat) {
	  this.wkt = String.format("POINT( %.2f %.2f )",lon,lat);
   }
   public Long getId() {
		return id;
   }	

	public void setId(Long Id) {
		this.id = Id;
	}
	
	
	public String getName() {
		return name;
	}
	
	
	public void setName(String name) {
		this.name = name;
	}
	
	
	public String getWkt() {
		return wkt;
	}
	
	
	public void setWkt(String wkt) {
		this.wkt = wkt;
	}
	
	
	public Set<OpenNode> getNodes() {
		return nodes;
	}
	
	
	public void setNodes(Set<OpenNode> nodes) {
		this.nodes = nodes;
	}
	
	
	public void setLocation(float lon, float lat) {
	      this.wkt = String.format("POINT( %.2f %.2f )",lon,lat);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
