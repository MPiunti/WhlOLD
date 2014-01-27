package org.neo4j.example.unmanagedextension;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.map.ObjectMapper;
import org.neo4j.graphdb.DynamicLabel;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.ResourceIterable;
import org.neo4j.graphdb.Transaction;


/**
 * Base Traverslals Path navigation 
 * MOVIMENTAZIONI MIN SALUTE
 * @author m.piunti
 * @date Jan 2014
 *
 */
@Path("/mp")
public class PathService_mp{
		
    @GET
    @Path("/test")
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
    	    
    	// first two characters   	
    	Label label = DynamicLabel.label(startNode.substring(0, 2));
    	// transaction is needed  @since 2.0
    	// @seealso http://stackoverflow.com/questions/21110677/neo4j-index-does-does-not-work-using-java-api
    	Map<String, Object> retMap;  	
    	ObjectMapper objectMapper = new ObjectMapper();
    	
    	
    	try{    	   
    	   Transaction tx = graphDb.beginTx();    		
    	   //ResourceIterable<Node> nodes_it = graphDb.findNodesByLabelAndProperty(label, "name", startNode);
    	   Node baseNode = graphDb.findNodesByLabelAndProperty(label, "name", startNode).iterator().next();    	   
    	   tx.success();
    	   
    	   // Traverse sub graph
    	   GraphStructure subGraph = (date1<date2) ? exploreFromNode(baseNode, date1, date2) : exploreToNode(baseNode, date1, date2);
    	    	        
    	   // fill Response
 	       retMap = prepareJSON(subGraph.getNode_map().values(), subGraph.getRelations_map().values());

    	}catch(Exception e){
    		retMap = new HashMap<String, Object>();
    		retMap.put("response", "KO");
    		retMap.put("exception", e.getMessage());
    	}     
    	
    	return Response.ok().entity(objectMapper.writeValueAsString(retMap)).build();

    }
    
    
    /**
     * Exploring FORWARD starting form a baseNode
     * @param baseNode
     * @param date1 starting date
     * @param date2 finish date
     * @return Graph structure subgraph
     */
    private GraphStructure exploreFromNode(Node baseNode, Long dateMin, Long dateMax){    	
    	
       GraphStructure subGraph = new GraphStructure();
       Map<Long, TimedNode> node_map = new LinkedHashMap<Long, TimedNode>();
       Map<Long, Relationship> relations_map = new LinkedHashMap<Long, Relationship>();
    	
 	   // Fill structure
 	   
 	   TimedNode basetNode = new TimedNode();
 	   basetNode.setNode(baseNode);
 	   basetNode.setTime(dateMin);
 	   
 	   if(node_map.get(baseNode.getId()) == null) {
 		  // this node has not been visited yet
		  node_map.put(baseNode.getId(), basetNode); 	  
 	   
	      Iterator<Relationship> it = baseNode.getRelationships().iterator();
	      Long dt_mov;
	      Relationship rel;
	      
	      while( it.hasNext() ){
        	rel = it.next();
        	dt_mov = Long.parseLong(rel.getProperty("data_movimento_ts").toString());
        	// take just OUTGOING relationships
        	if(rel.getStartNode().getId() == baseNode.getId() 
        			&& dt_mov<=dateMax && dt_mov>=dateMin){ 	
        		if(!relations_map.containsKey(rel.getId()) )
        		    relations_map.put(rel.getId(), rel);
        		
        		GraphStructure ngs = exploreFromNode(rel.getEndNode(), dt_mov, dateMax);
        		
        		relations_map.putAll(ngs.getRelations_map());
        		node_map.putAll(ngs.getNode_map());
        	}
	       } 
 	   }
	        
       subGraph.setNode_map(node_map);
       subGraph.setRelations_map(relations_map);
        
       return subGraph;
    	
    }
    
    /**
     * Exploring BACKWARD starting form a baseNode
     * @param baseNode
     * @param date1 finish date
     * @param date2 starting date
     * @return Graph structure subgraph
     */
    private GraphStructure exploreToNode(Node baseNode, Long dateMax, Long dateMin){    	
    	
       GraphStructure subGraph = new GraphStructure();
       Map<Long, TimedNode> node_map = new LinkedHashMap<Long, TimedNode>();
       Map<Long, Relationship> relations_map = new LinkedHashMap<Long, Relationship>();
    	
 	   // Fill structure
 	   
 	   TimedNode basetNode = new TimedNode();
 	   basetNode.setNode(baseNode);
 	   basetNode.setTime(dateMax);
 	   
 	   if(node_map.get(baseNode.getId()) == null) {
 		  // this node has not been visited yet
		  node_map.put(baseNode.getId(), basetNode); 	  
 	   
	      Iterator<Relationship> it = baseNode.getRelationships().iterator();
	      Long dt_mov;
	      Relationship rel;
	      
	      while( it.hasNext() ){
        	rel = it.next();
        	dt_mov = Long.parseLong(rel.getProperty("data_movimento_ts").toString());
        	// take just INGOING relationships
        	if(rel.getEndNode().getId() == baseNode.getId() 
        			&& dt_mov>=dateMin && dt_mov<=dateMax){ 	
        		if(!relations_map.containsKey(rel.getId()) )
        		    relations_map.put(rel.getId(), rel);
        		
        		GraphStructure ngs = exploreToNode(rel.getStartNode(), dt_mov, dateMin);
        		
        		relations_map.putAll(ngs.getRelations_map());
        		node_map.putAll(ngs.getNode_map());
        	}
	       } 
 	   }
	        
       subGraph.setNode_map(node_map);
       subGraph.setRelations_map(relations_map);
        
       return subGraph;
    	
    }
    

    
    /**
     *   Prepare JSON Response
     * @param node_list
     * @param relations_list
     * @return
     */
    private Map<String, Object> prepareJSON(Collection<TimedNode> node_list, Collection<Relationship> relations_list) {
    	
    	Map<String, Object> retMap = new HashMap<String, Object>();
    	
	    List<Object> nodes = new ArrayList<Object>();
		for ( TimedNode tnode : node_list )    {
			
		     Map<String, Object> nodeMap = new HashMap<String, Object>();
		     Node node = tnode.getNode();
		     nodeMap.put("id", node.getId());
		     nodeMap.put("name", node.getProperty("name"));
		     for(Label l: node.getLabels() ){
		    	 nodeMap.put("type_label", l.name() );
		     }
		     nodeMap.put("ragione_sociale", node.getProperty("ragione_sociale",null));
		     nodes.add(nodeMap);
		    }
		retMap.put("nodes", nodes);
		 
		List<Object> relationships = new ArrayList<Object>();
		for ( Relationship relationship : relations_list  )   {
	         Map<String, Object> relMap = new HashMap<String, Object>();
	         relMap.put("id", relationship.getId());
	         // relMap.put("type", relationship.getType() );
	         relMap.put("start_node", relationship.getStartNode().getId());
	         relMap.put("end_node", relationship.getEndNode().getId());
	         relMap.put("data_movimento_ts", relationship.getProperty("data_movimento_ts", null));
	         relMap.put("num_capi_nodo_orig", relationship.getProperty("num_capi_nodo_orig", null));
	         relMap.put("num_capi_nodo_dest	", relationship.getProperty("num_capi_nodo_dest", null));
	         relMap.put("num_capi_movimentati", relationship.getProperty("num_capi_movimentati", null));
	         relMap.put("data_movimento", relationship.getProperty("data_movimento", null));
	         relationships.add(relMap);
		}
		
		retMap.put("relationships", relationships);
		retMap.put("response", "OK");
		
		return retMap;
    }
    
    
   
 
}












