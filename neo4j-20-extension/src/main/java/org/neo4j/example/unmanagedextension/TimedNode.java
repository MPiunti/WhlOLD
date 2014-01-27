package org.neo4j.example.unmanagedextension;

import org.neo4j.graphdb.Node;

public class TimedNode {
	
	Node node;
	Long time;
	
	
	public Node getNode() {
		return node;
	}
	public void setNode(Node node) {
		this.node = node;
	}
	public Long getTime() {
		return time;
	}
	public void setTime(Long time) {
		this.time = time;
	}
}
