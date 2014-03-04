package org.neo4j.example.unmanagedextension;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.server.logging.Logger;

@Path("/path")
public class PathController {
	
	Logger logger = Logger.getLogger(this.getClass());
	
	private PathService pathService = new PathService();
	
	@GET
    @Path("/test")
    public String helloWorld(@Context GraphDatabaseService graphDb) {
        String ret = "Hello World!";
        logger.info("Test find");
        return ret;
    }
	
	@GET
    @Path("/find/{startNode}/{startDate}/{endDate}")
    @Produces("application/json")
    public Response find (@Context GraphDatabaseService graphDb
    		, @PathParam("startNode") String startNode
    		, @PathParam("startDate") Long startDate
    		, @PathParam("endDate") Long endDate) throws Exception {
		pathService.setGraphDb(graphDb);
        return pathService.find(startNode, startDate, endDate);
    }
    
    @GET
    @Path("/find/{startNode}")
    @Produces("application/json")
    public Response find (@Context GraphDatabaseService graphDb
    		, @PathParam("startNode") String startNode) throws Exception {
    	return find(graphDb, startNode, null, null);
    }
    
    @GET
    @Path("/find/startbyday/{startNode}")
    @Produces("application/json")
    public Response startbyday (@Context GraphDatabaseService graphDb
    		, @PathParam("startNode") String startNode
    		, @PathParam("startDate") Long startDate) throws Exception {
    	return find(graphDb, startNode, startDate, null);
    }
    
    @GET
    @Path("/find/endbyday/{endDate}")
    @Produces("application/json")
    public Response endbyday (@Context GraphDatabaseService graphDb
    		, @PathParam("startNode") String startNode
    		, @PathParam("endDate") Long endDate) throws Exception {
    	return find(graphDb, startNode, null, endDate);
    }
    
    @GET
    @Path("/view{viewname : (/[a-z_\\-\\s0-9\\.]+)+\\.\\w+$}")
    public Response view (@PathParam("viewname") String viewName) throws Exception {
    	return pathService.view(viewName);
    }
    
    @GET
    @Path("/nodes/{nodeName}")
    public Response getNodeByName (@Context GraphDatabaseService graphDb, @PathParam("nodeName") String nodeName
    		, @QueryParam("limit") Integer limit) throws Exception {
		pathService.setGraphDb(graphDb);
    	return pathService.getNodeByName(nodeName, limit);
    }
}
