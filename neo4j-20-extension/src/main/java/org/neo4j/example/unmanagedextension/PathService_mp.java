package org.neo4j.example.unmanagedextension;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
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
    //@Produces("application/json")
    public String find ( @PathParam("startNode") String startNode
    		, @PathParam("date1") Long date1
    		, @PathParam("date2") Long date2
    		, @Context GraphDatabaseService graphDb) throws IOException {

    	String ret="";     	    
    	// first two characters   	
    	Label label = DynamicLabel.label(startNode.substring(0, 2));
    	// transaction is needed 
    	// @since 2.0
    	// http://stackoverflow.com/questions/21110677/neo4j-index-does-does-not-work-using-java-api
    	try{
    	   
    	   Transaction tx = graphDb.beginTx();
    		
    	   ResourceIterable<Node> nodes = graphDb.findNodesByLabelAndProperty(label, "name", startNode);
 	       Iterator<Relationship> it = nodes.iterator().next().getRelationships().iterator();
 	       Long dt_mov;
 	       Relationship rel;
 	        while( it.hasNext() ){
 	        	rel = it.next();
 	        	dt_mov = Long.parseLong(rel.getProperty("data_movimento_ts").toString() );
 	        	if((dt_mov >= date1) && (dt_mov <= date2))
 	        		ret +=  "node data_movimento_ts: " + rel.getProperty("data_movimento_ts") + "<br/>";
 	        }
    		tx.success();

    	}catch(Exception e){
    		
    	}
        
        
        
       	//ExecutionEngine executionEngine = new ExecutionEngine(graphDb);
    	String query = "<br/><br/> MATCH (n { name: '%s'})	RETURN n " +  label.name();
    	ret += "\n \n" + String.format(query,startNode);
    	
    	
    			//"START n = node:struttura(id={startNode}) RETURN ID(n) as nodeId";
 
    	return ret;
        /*ExecutionResult result = executionEngine.execute(String.format(query,startNode), 
                Collections.<String, Object>singletonMap("startNode", startNode));
        Long startNodeID = (Long) result.iterator().next().get("id");
        
        logger.info("Start node id: " + startNodeID);
        
    	Node neoNode = graphDb.getNodeById(startNodeID);

    	
    	String strret;
    	
    	if(date1<date2){
    		 strret = " the node:" + neoNode.getProperty("name") + "(id{}) is a starting node" + startNodeID;
    	} else {
    		strret =  " the node:" + neoNode.getProperty("name") + "(id{}) is an arriving node" + startNodeID;
    	}
    	
   	   
   	    return strret;
    	

        /*
        Set<Map<String, Object>> nodes = new LinkedHashSet<Map<String, Object>>();
        Set<Map<String, Object>> relationships = new LinkedHashSet<Map<String, Object>>();
        Long ultimoMovimento = 0L;
        for (org.neo4j.graphdb.Path friendPath : friendsTraverser) {
        	for (Relationship relationship : friendPath.relationships()) {
        		Long dataMovimento = (Long) relationship.getProperty("data_movimento");
        		logger.info("StartDate: %d\nEndDate: %d\nDataMovimento: %d"
        				, start, end, dataMovimento);
				if (ultimoMovimento > dataMovimento) {
					continue;
				}
				ultimoMovimento = dataMovimento;
    			if (dataMovimento >= start && dataMovimento <= end) {
    				Map<String, Object> node = toMap(friendPath.endNode());
    				node.put("nId", friendPath.endNode().getId());
    				nodes.add(node);
    				Map<String, Object> rel = toMap(relationship);
    				rel.put("source", relationship.getStartNode().getId());
    				rel.put("target", node.get("nId"));
    				rel.put("rId", relationship.getId());
    				relationships.add(rel);
    				logger.info("NodeId: %d, RelationshipId: %d, at depth: %d", friendPath.endNode().getId()
    						, relationship.getId(), friendPath.length());
    			}
        	}
        }
        ObjectMapper objectMapper = new ObjectMapper();
        logger.info("Nodes size: %d\nRelationships size: %d", nodes.size(), relationships.size());
    	
        
        response.put("nodes", nodes);
        response.put("relationships", relationships);
        return Response.ok().entity(objectMapper.writeValueAsString(response)).build();
        */
    	

    }
    
   /* @GET
    @Path("/find/{startNode}")
    @Produces("application/json")
    public Response find (@Context GraphDatabaseService graphDb, @PathParam("startNode") String startNode) throws Exception {
    	return find(graphDb, startNode, null, null);
    }
    
    @GET
    @Path("/find/startbyday/{startNode}")
    @Produces("application/json")
    public Response startbyday (@Context GraphDatabaseService graphDb, @PathParam("startNode") String startNode
    		, @PathParam("startDate") String startDate) throws Exception {
    	return find(graphDb, startNode, startDate, null);
    }
    
    @GET
    @Path("/find/endbyday/{endDate}")
    @Produces("application/json")
    public Response endbyday (@Context GraphDatabaseService graphDb, @PathParam("startNode") String startNode
    		, @PathParam("endDate") String endDate) throws Exception {
    	return find(graphDb, startNode, null, endDate);
    }
    */
    
    
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















