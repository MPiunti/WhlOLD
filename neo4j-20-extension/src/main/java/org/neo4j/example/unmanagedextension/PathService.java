package org.neo4j.example.unmanagedextension;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.core.Response;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.IteratorUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
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
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.traversal.Evaluators;
import org.neo4j.graphdb.traversal.TraversalDescription;
import org.neo4j.graphdb.traversal.Traverser;
import org.neo4j.graphdb.traversal.Uniqueness;
import org.neo4j.server.logging.Logger;

import com.sun.jersey.spi.resource.Singleton;

/**
 * @author Andrea
 * 
 */
@Singleton
public class PathService {

	Logger logger = Logger.getLogger(this.getClass());

	public enum RelTypes implements RelationshipType {
		MOVES
	}

	public GraphDatabaseService graphDb;

	public String helloWorld() {
		String ret = "Hello World!";
		logger.info("Test find");
		return ret;
	}
	
	public ObjectMapper objectMapper = setObjectMapper();

	@SuppressWarnings("unchecked")
	public Response find(String startNode, Long startDate, Long endDate)
			throws Exception {
		Date now = new Date();
		Label label = DynamicLabel.label(startNode.substring(0, 2));
		Node neoNode = null;
		Map<String, Object> response = new HashMap<String, Object>();
		if (graphDb == null) {
			logger.info("graphDb is null");
			response.put("error", "graphDb is null");
			return Response.serverError().entity(response).build();
		}
		try {
			Transaction tx = graphDb.beginTx();
			neoNode = graphDb
					.findNodesByLabelAndProperty(label, "name", startNode)
					.iterator().next();
			tx.success();
		} catch (Exception e) {
			logger.info("Errore: " + e.getMessage());
			return Response.serverError().build();
		}
		if (neoNode == null) {
			response.put("errore", "Nodo di partenza non trovato");
			return Response.noContent().entity(response).build();
		}
		Long start = startDate != null ? startDate : 0;
		Long end = endDate != null ? endDate : Long.MAX_VALUE;
		boolean isBackward = start > end;
		Traverser friendsTraverser = getFriends(neoNode, isBackward, start, end);
		Set<Map<String, Object>> nodes = new LinkedHashSet<Map<String, Object>>();
		nodes.add(toMap(neoNode));
//		Set<Map<String, Object>> relationships = new LinkedHashSet<Map<String, Object>>();
		List<Map<String, Object>> relationships = new ArrayList<Map<String, Object>>();
		for (org.neo4j.graphdb.Path friendPath : friendsTraverser) {
			List<Relationship> rels = IteratorUtils.toList(friendPath
					.relationships().iterator());
			for (Relationship relationship : rels) {
				nodes.add(toMap(relationship.getStartNode()));
				nodes.add(toMap(relationship.getEndNode()));
				relationships.add(relationshipToMap(relationship));
			}
		}
		logger.info("Nodi trovati: %d\nRelazioni trovate: %d", nodes.size(),
				relationships.size());
		nodes.iterator().next().put("isStart", true);
		response.put("nodes", nodes);
		response.put("relationships", relationships);
		Date now2 = new Date();
		logger.info("Time: " + ((now2.getTime() - now.getTime()) / 1000));
		return Response.ok().entity(objectMapper.writeValueAsString(response))
				.build();
	}

	private ObjectMapper setObjectMapper() {
		if (objectMapper == null) {
			objectMapper = new ObjectMapper();
		}
		return objectMapper;
	}

	public Response find(String startNode) throws Exception {
		return find(startNode, null, null);
	}

	public Response startbyday(String startNode, Long startDate)
			throws Exception {
		return find(startNode, startDate, null);
	}

	public Response endbyday(String startNode, Long endDate) throws Exception {
		return find(startNode, null, endDate);
	}

	private Traverser getFriends(final Node node, boolean isBackward,
			Long start, Long end) {
		TraversalDescription td = graphDb
				.traversalDescription()
				.depthFirst()
				// .breadthFirst()
				.relationships(RelTypes.MOVES,
						!isBackward ? Direction.OUTGOING : Direction.INCOMING)
				.evaluator(Evaluators.excludeStartPosition())
				.uniqueness(Uniqueness.NODE_PATH)
				// .uniqueness(Uniqueness.NODE_GLOBAL)
				.evaluator(getPathFilter(isBackward, start, end));
		return td.traverse(node);
	}

	private static PathFilter getPathFilter(boolean isBackward, Long start,
			Long end) {
		return !isBackward ? new PathFilter(start, end) : new PathFilter(end,
				start);
	}

	private Map<String, Object> toMap(PropertyContainer node) {
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

	public Map<String, Object> relationshipToMap(PropertyContainer relationship) {
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
		map.put("sourceName", start.getProperty("name"));
		map.put("targetName", end.getProperty("name"));
		return map;
	}

	public Response view(String viewName) throws Exception {
		String path = System.getProperty("user.dir");
		File currentFile = new File(path);
		String[] views = viewName.split("/");
		File view = new File(currentFile.getParentFile()
				+ "\\plugins\\6degrees\\" + StringUtils.join(views, "\\"));
		logger.info("View path: %s", view.getPath());
		String contentType = view.toURI().toURL().openConnection()
				.getContentType();
		logger.info("Mime Type is: %s", contentType);
		if ("content/unknown".equals(contentType)) {
			if (viewName.endsWith("js")) {
				contentType = "application/javascript";
			} else if (viewName.endsWith("css")) {
				contentType = "text/css";
			}
			logger.info("Is unknown then: %s", contentType);
		}
		return Response.ok().entity(FileUtils.readFileToString(view, "UTF-8"))
				.header("Content-Type", contentType + "; charset=UTF-8")
				.build();
	}
	
	
	
	public Response getNodeByName(String nodeName, Integer limit) throws Exception {
		String query = "MATCH (n) WHERE n.name =~ '%s.*' RETURN n";
		if (limit != null && limit > 0) {
			query += String.format(" LIMIT %d", limit);
		}
		ExecutionEngine executor = new ExecutionEngine(graphDb);
		
		Set<Map<String, Object>> nodes = new LinkedHashSet<Map<String, Object>>();
		try {
			Transaction tx = graphDb.beginTx();
			ExecutionResult result = executor.execute(String.format(query, nodeName));
			Iterator<Node> nodeIterator = result.columnAs("n");
			while (nodeIterator.hasNext()) {
				nodes.add(toMap(nodeIterator.next()));
			}
			tx.success();
		} catch (Exception e) {
			e.printStackTrace();
			Map<String, Object> response = new HashMap<String, Object>();
			response.put("errore", "Nodo di partenza non trovato");
			return Response.noContent().entity(response).build();
		}
		return Response.ok().entity(objectMapper.writeValueAsString(nodes)).build();
	}

	public static void main(String[] args) {
		GraphDatabaseService graphService = new GraphDatabaseFactory()
				// .newEmbeddedDatabaseBuilder("C:\\Users\\m.piunti\\Desktop\\batch_importer_20\\movH1-13.db")
				.newEmbeddedDatabaseBuilder("C:\\Users\\Andrea\\Desktop\\movH1-13.db")
				.newGraphDatabase();
		try {
			SimpleDateFormat sf = new SimpleDateFormat("dd-MM-yyyy");
			PathService traversingService = new PathService();
			traversingService.setGraphDb(graphService);

			String nodeName = "SS115BS060";
			String startDateS = "24-04-2013";
			// String[] endDates = {"26-04-2013", "28-04-2013", "30-04-2013",
			// "05-05-2013", "10-05-2013", "15-05-2013", "20-05-2013",
			// "25-05-2013"};
//			String[] endDates = { "28-04-2013" };
			Integer[] endDates = { 2,3,5,7,9,11,13,15,20,30,40,50 };
			Date startDate = sf.parse(startDateS);
			Long startDateL = startDate.getTime();
			System.out.println(traversingService.getNodeByName("SS1", 10).getEntity());
			for (Integer endDate : endDates) {
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(startDate);
				calendar.add(Calendar.DAY_OF_MONTH, endDate);
				Long endDateL = calendar.getTimeInMillis();//sf.parse(endDate).getTime();
				System.out.println(String.format(
						"Nodo: %s, From: %s, To: %s, Tipo: FORWARD", nodeName,
						startDateS, calendar.getTime()));
				System.out.println(String.format(
						"Url: http://localhost:7474/moves/path/find/%s/%d/%d",
						nodeName, startDateL, endDateL));
//				System.out.println(traversingService.find(nodeName, startDateL, endDateL).getEntity());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setGraphDb(GraphDatabaseService graphDb) {
		if (this.graphDb == null) {
			this.graphDb = graphDb;
		}
	}
}
