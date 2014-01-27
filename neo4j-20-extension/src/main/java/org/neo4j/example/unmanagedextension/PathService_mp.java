package org.neo4j.example.unmanagedextension;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.map.ObjectMapper;
import org.neo4j.cypher.javacompat.ExecutionEngine;
import org.neo4j.cypher.javacompat.ExecutionResult;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.DynamicLabel;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.PropertyContainer;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.ResourceIterable;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.traversal.Evaluators;
import org.neo4j.graphdb.traversal.TraversalDescription;
import org.neo4j.graphdb.traversal.Traverser;
import org.neo4j.kernel.Traversal;
import org.neo4j.server.logging.Logger;

/**
 * Base Traverslals Path navigation
 * @author m.piunti
 *
 */
@Path("/mp")
public class PathService_mp{
	
	Logger logger = Logger.getLogger(this.getClass());
	
	private String dateFormat = "dd-MM-yyyy";
	
	public enum RelTypes implements RelationshipType {
        //MOVIMENTO,
        MOVES
    }
	
    @GET
    @Path("/hi")
    public String helloWorld(@Context GraphDatabaseService graphDb) {
        String ret = "Hello World!";
        Label label = DynamicLabel.label("AL");
        ResourceIterable<Node> nodes = graphDb.findNodesByLabelAndProperty(label, "name", "AL024RE023");
        Iterator<Relationship> it = nodes.iterator().next().getRelationships().iterator();
        while( it.hasNext() )
        	ret += it.next().getProperty("data_movimento_ts") + "<br/>";
        
        return ret;
    }
	
    @GET
    @Path("/find/{startNode}/{date1}/{date2}")
    @Produces("application/json")
    public Response find ( @PathParam("startNode") String startNode
    		, @PathParam("date1") Long date1
    		, @PathParam("date2") Long date2
    		, @Context GraphDatabaseService graphDb) throws IOException {

    	String ret="";     	    
    	// first two characters   	
    	Label label = DynamicLabel.label(startNode.substring(0, 2));
    	// transaction is needed 
    	// @since 2.0
    	// http://stackoverflow.com/questions/21110677/neo4j-index-does-does-not-work-using-java-api
    	Map<String, Object> retMap = new HashMap<String, Object>();
    	List<Node> node_list = new ArrayList<Node>();
    	List<Relationship> relations_list = new ArrayList<Relationship>();
    	
    	ObjectMapper objectMapper = new ObjectMapper();
    	
    	
    	try{    	   
    	   Transaction tx = graphDb.beginTx();
    		
    	   //ResourceIterable<Node> nodes_it = graphDb.findNodesByLabelAndProperty(label, "name", startNode);
    	   Node baseNode = graphDb.findNodesByLabelAndProperty(label, "name", startNode).iterator().next();
    	   node_list.add(baseNode);
    	   
 	       Iterator<Relationship> it = baseNode.getRelationships().iterator();
 	       Long dt_mov;
 	       Relationship rel;
 	      
 	        while( it.hasNext() ){
 	        	rel = it.next();
 	        	// take just outgoing relationships
 	        	if(rel.getStartNode().getId() == baseNode.getId()){
 	        		relations_list.add(rel);
 	        		node_list.add(rel.getEndNode());
 	        	}
 	        	/*dt_mov = Long.parseLong(rel.getProperty("data_movimento_ts").toString() );
 	        	if((dt_mov >= date1) && (dt_mov <= date2))
 	        		ret +=  "node data_movimento_ts: " + rel.getProperty("data_movimento_ts") + "<br/>";*/
 	        }
    		tx.success(); 	  

    	
	    	 
	    	List<Object> nodes = new ArrayList<Object>();
	    	for ( Node node : node_list )
	    	    {
	    	     Map<String, Object> nodeMap = new HashMap<String, Object>();
	    	     nodeMap.put("id", node.getId());
	    	     nodeMap.put("name", node.getProperty("name"));
	    	     for(Label l: node.getLabels() ){
	    	    	 nodeMap.put("type_label", l.name() );
	    	     }
	    	     //nodeMap.put("ragione_sociale", node.getProperty("ragione_sociale"));
	    	     nodes.add(nodeMap);
	    	    }
	    	retMap.put("nodes", nodes);
	    	 
	    	List<Object> relationships = new ArrayList<Object>();
	    	for ( Relationship relationship : relations_list  )
	    	    {
	    	         Map<String, Object> relMap = new HashMap<String, Object>();
	    	         relMap.put("id", relationship.getId());
	    	        // relMap.put("type", relationship.getType() );
	    	         relMap.put("start_node", relationship.getStartNode().getId());
	    	         relMap.put("end_node", relationship.getEndNode().getId());
	    	         relMap.put("data_movimento_ts", relationship.getProperty("data_movimento_ts"));
	    	      //   relMap.put("data_movimento", relationship.getProperty("data_movimento"));
	    	         relationships.add(relMap);
	    	    }
	    	 
	    	retMap.put("relationships", relationships);    	 
	    	

	    	retMap.put("response", "OK");
	    	
	    	return Response.ok().entity(objectMapper.writeValueAsString(retMap)).build();     
    	
    	}catch(Exception e){
    		retMap.put("response", "KO");
    		retMap.put("exception", e.getMessage());
    		return Response.ok().entity(objectMapper.writeValueAsString(retMap)).build();    
    	}     

    }
    
 
    
    
    @Deprecated
    private static Traverser getFriends (final Node node) {
        TraversalDescription td = Traversal.description()
                .breadthFirst()
                .relationships(RelTypes.MOVES, Direction.OUTGOING)
                .evaluator(Evaluators.excludeStartPosition());
        return td.traverse(node);
    }

    private static Traverser getFriends (final GraphDatabaseService graphDb, final Node node) {
        TraversalDescription td = graphDb.traversalDescription()
                .breadthFirst()
                .relationships(RelTypes.MOVES, Direction.OUTGOING)
                .evaluator(Evaluators.excludeStartPosition());
        return td.traverse(node);
    }
    
    private Date parseDate (String strDate) throws Exception {
    	return new SimpleDateFormat(dateFormat, Locale.ITALIAN).parse(strDate);
    }
    
    private Map<String, Object> toMap (PropertyContainer node) {
    	Map<String, Object> nodeMap = new HashMap<String, Object>();
    	for (String key : node.getPropertyKeys()) {
    		nodeMap.put(key, node.getProperty(key));
    	}
    	return nodeMap;
    }
    
    private Long parseDateToTimestamp (String date) throws Exception {
    	return parseDate(date).getTime();
    }
    
    private Long parseDateToMillis (String date) throws Exception {
    	Calendar cal = Calendar.getInstance();
        cal.setTime(parseDate(date));
        long timeStamp = cal.getTimeInMillis();
        return timeStamp;
    }
}















