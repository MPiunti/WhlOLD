package org.neo4j.example.unmanagedextension;


import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
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
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.PropertyContainer;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.traversal.Evaluators;
import org.neo4j.graphdb.traversal.TraversalDescription;
import org.neo4j.graphdb.traversal.Traverser;
import org.neo4j.kernel.Traversal;
import org.neo4j.server.logging.Logger;

@Path("/path")
public class PathService_Conker {
	
	Logger logger = Logger.getLogger(this.getClass());
	
	private String dateFormat = "dd-MM-yyyy";
	
	public enum RelTypes implements RelationshipType {
        MOVIMENTO,
        MOVES
    }
	
    @GET
    @Path("/find/{startNode}/{startDate}/{endDate}")
    @Produces("application/json")
    public Response find (@Context GraphDatabaseService graphDb, @PathParam("startNode") String startNode
    		, @PathParam("startDate") String startDate
    		, @PathParam("endDate") String endDate) throws Exception {
    	logger.info("Test find");
    	ExecutionEngine executionEngine = new ExecutionEngine(graphDb);
    	String query = "START n = node:stalla(id={startNode}) RETURN ID(n) as nodeId";
        ExecutionResult result = executionEngine.execute(String.format(query, startNode),
                Collections.<String, Object>singletonMap("startNode", startNode));
        Long startNodeID = (Long) result.iterator().next().get("nodeId");
    	Node neoNode = graphDb.getNodeById(startNodeID);
        Traverser friendsTraverser = getFriends(neoNode);
        Long start = startDate != null ? parseDateToTimestamp(startDate) : 0;
        Long end = endDate != null ? parseDateToTimestamp(endDate) : Long.MAX_VALUE;
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
        Map<String, Set<Map<String, Object>>> response = new HashMap<String, Set<Map<String, Object>>>();
        response.put("nodes", nodes);
        response.put("relationships", relationships);
        return Response.ok().entity(objectMapper.writeValueAsString(response)).build();
    }
    
    @GET
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

    private static Traverser getFriends (final Node node) {
        TraversalDescription td = Traversal.description()
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
}
