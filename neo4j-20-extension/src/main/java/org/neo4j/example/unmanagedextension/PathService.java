package org.neo4j.example.unmanagedextension;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.IteratorUtils;
import org.apache.commons.lang.time.StopWatch;
import org.codehaus.jackson.map.ObjectMapper;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.DynamicLabel;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.PropertyContainer;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.traversal.Evaluators;
import org.neo4j.graphdb.traversal.TraversalDescription;
import org.neo4j.graphdb.traversal.Traverser;
import org.neo4j.graphdb.traversal.Uniqueness;
import org.neo4j.server.logging.Logger;

@Path("/path")
public class PathService {
	
	Logger logger = Logger.getLogger(this.getClass());
	
//	private String dateFormat = "dd-MM-yyyy";
	
	public enum RelTypes implements RelationshipType {
        MOVES
    }
	
	@GET
    @Path("/test")
    public String helloWorld(@Context GraphDatabaseService graphDb) {
        String ret = "Hello World!";
        logger.info("Test find");
        return ret;
    }
	
    @SuppressWarnings("unchecked")
	@GET
    @Path("/find/{startNode}/{startDate}/{endDate}")
    @Produces("application/json")
    public Response find (@Context GraphDatabaseService graphDb
    		, @PathParam("startNode") String startNode
    		, @PathParam("startDate") Long startDate
    		, @PathParam("endDate") Long endDate) throws Exception {
    	Date now = new Date();
    	logger.info("Test find");
    	Label label = DynamicLabel.label(startNode.substring(0, 2));
    	Node neoNode = null;
        ObjectMapper objectMapper = new ObjectMapper();
        if (graphDb == null) {
        	logger.info("graphDb is null");
        	return Response.serverError().build();
        }
    	try{    	   
     	   Transaction tx = graphDb.beginTx();    		
     	   neoNode = graphDb.findNodesByLabelAndProperty(label, "name", startNode).iterator().next();
     	   tx.success();
     	}catch(Exception e){
     		logger.info("Errore: " + e.getMessage());
        	return Response.serverError().build();
     	}
        Long start = startDate != null ? startDate : 0;
        Long end = endDate != null ? endDate : Long.MAX_VALUE;
        boolean isBackward = start > end;
        Traverser friendsTraverser = getFriends(graphDb, neoNode, isBackward, start, end);
        Set<Map<String, Object>> nodes = new LinkedHashSet<Map<String, Object>>();
        Set<Map<String, Object>> relationships = new LinkedHashSet<Map<String, Object>>();
        nodes.add(toMap(neoNode));
        for (org.neo4j.graphdb.Path friendPath : friendsTraverser) {
        	List<Relationship> rels = IteratorUtils.toList(friendPath.relationships().iterator());
        	rels = (List<Relationship>) CollectionUtils.select(rels, getPathFilter(isBackward, start, end));
        	for (Relationship relationship : rels) {
				nodes.add(toMap(relationship.getStartNode()));
				nodes.add(toMap(relationship.getEndNode()));
				relationships.add(relationshipToMap(relationship));
        	}
        }
        logger.info("NODES FOUND: %d\nRELATIONSHIPS size: %d", nodes.size(), relationships.size());
        Map<String, Set<Map<String, Object>>> response = new HashMap<String, Set<Map<String, Object>>>();
        response.put("nodes", nodes);
        response.put("relationships", relationships);
    	Date now2 = new Date();
    	logger.info("Time: " + ((now2.getTime() - now.getTime()) / 1000));
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
    		, @PathParam("startDate") Long startDate) throws Exception {
    	return find(graphDb, startNode, startDate, null);
    }
    
    @GET
    @Path("/find/endbyday/{endDate}")
    @Produces("application/json")
    public Response endbyday (@Context GraphDatabaseService graphDb, @PathParam("startNode") String startNode
    		, @PathParam("endDate") Long endDate) throws Exception {
    	return find(graphDb, startNode, null, endDate);
    }

    private static Traverser getFriends (final GraphDatabaseService graphDb, final Node node, boolean isBackward,
    		Long start, Long end) {
//    	class PathEvaluator implements Evaluator {
//            Long ultimoMovimento = 0L
//            		, start = 0L
//            		, end = 0L;
//            public PathEvaluator(Long start, Long end) {
//            	this.start = start;
//            	this.end = end;
//            }
//			public Evaluation evaluate(org.neo4j.graphdb.Path path) {
//				for (Relationship rel : path.relationships()) {
//					ultimoMovimento = (Long) rel.getProperty("data_movimento_ts");
//	    			if (ultimoMovimento >= start && ultimoMovimento <= end) {
//	    				return Evaluation.INCLUDE_AND_CONTINUE;
//	    			}
//				}
//    			return CollectionUtils.size(path.relationships()) > 0 ? Evaluation.EXCLUDE_AND_PRUNE : Evaluation.EXCLUDE_AND_CONTINUE;
//			}
//		}
    	TraversalDescription td = graphDb.traversalDescription()
                .breadthFirst()
                .relationships(RelTypes.MOVES, !isBackward ? Direction.OUTGOING : Direction.INCOMING)
                .evaluator(Evaluators.excludeStartPosition())
                .uniqueness(Uniqueness.NODE_GLOBAL)//Ogni nodo viene visitato una sola volta
                .evaluator(getPathFilter(isBackward, start, end));
        return td.traverse(node);
    }
    
    private static PathFilter getPathFilter(boolean isBackward, Long start, Long end) {
    	return !isBackward ? new PathFilter(start, end) : new PathFilter(end, start);
    }
    
    private Map<String, Object> toMap (PropertyContainer node) {
    	Map<String, Object> nodeMap = new HashMap<String, Object>();
    	for (String key : node.getPropertyKeys()) {
    		nodeMap.put(key, node.getProperty(key));
    	}
    	try {
			nodeMap.put("id", PropertyUtils.getProperty(node, "id"));
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return nodeMap;
    }
    
    public Map<String, Object> relationshipToMap (PropertyContainer relationship) {
    	Map<String, Object> map = toMap(relationship);
    	Node start = null, end = null;
		try {
			start = (Node) PropertyUtils.getProperty(relationship, "startNode");
	    	end = (Node) PropertyUtils.getProperty(relationship, "endNode");
		} catch (Exception e) {
			e.printStackTrace();
		}
    	map.put("source", start.getId());
    	map.put("target", end.getId());
    	return map;
    }
    
    
    
    public static void main(String[] args) {
    	GraphDatabaseService graphdb = new GraphDatabaseFactory()
    	.newEmbeddedDatabaseBuilder("C:\\Users\\m.piunti\\Desktop\\batch_importer_20\\movH1-13.db")
		       // .newEmbeddedDatabaseBuilder("C:\\Users\\Andrea\\Desktop\\movH1-13.db")    	
		        .newGraphDatabase();
		try {
			SimpleDateFormat sf = new SimpleDateFormat("dd-MM-yyyy");
			PathService traversingService = new PathService();
				
			long startTime = System.currentTimeMillis();
			traversingService.find(
					graphdb, 
					"SS115BS060", 
					sf.parse("13-12-2013").getTime(),
					sf.parse("12-01-2013").getTime()
					);	
			System.out.println(" BACKWARD ANALYSIS finalized in: " +  (System.currentTimeMillis() - startTime)/1000  + " sec.");
			
			startTime = System.currentTimeMillis();
			
			traversingService.find(
					graphdb, 
					"SS115BS060", 					
					sf.parse("12-01-2013").getTime(),
					sf.parse("13-12-2013").getTime()
					);		
			System.out.println(" FORWARD ANALYSIS finalized in: " +  (System.currentTimeMillis() - startTime)/1000  + " sec.");
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}