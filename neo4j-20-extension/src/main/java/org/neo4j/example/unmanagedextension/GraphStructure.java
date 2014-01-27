package org.neo4j.example.unmanagedextension;


import java.util.Map;

import org.neo4j.graphdb.Relationship;

public class GraphStructure {
	
	Map<Long, TimedNode> node_map;
	Map<Long, Relationship> relations_map;
	
	
	
	public Map<Long, TimedNode> getNode_map() {
		return node_map;
	}
	public void setNode_map(Map<Long, TimedNode> node_map) {
		this.node_map = node_map;
	}
	public Map<Long, Relationship> getRelations_map() {
		return relations_map;
	}
	public void setRelations_map(Map<Long, Relationship> relations_map) {
		this.relations_map = relations_map;
	}
	

}
