package eu.reply.whitehall.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.kernel.AbstractGraphDatabase;
import org.neo4j.rest.graphdb.RestGraphDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.core.GraphDatabase;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.csvreader.CsvReader;

import eu.reply.whitehall.domain.nodes.OpenDocument;
import eu.reply.whitehall.domain.nodes.OpenNode;
import eu.reply.whitehall.domain.nodes.User;
import eu.reply.whitehall.domain.relationships.DocumentNodeRelationship;
import eu.reply.whitehall.repository.OpenDocumentRepository;
import eu.reply.whitehall.repository.OpenNodeRepository;

@Service
public class CSVService {
	
	@Autowired 
	private Neo4jTemplate template;
	 
	/*@Autowired
	private UserRepository userRepository;*/
	@Autowired
	private OpenNodeRepository openNodeRepository;
	@Autowired
	private OpenDocumentRepository openDocumentRepository;
	 

    /*@Transactional
    public void importOpenRows(CsvReader csvDocument, OpenDocument openDocument) throws IOException {
    	
        // HEADRERS
    	csvDocument.readHeaders();
    	int OPEN_COLUMN = csvDocument.getHeaders().length;
    	String[] columns  = new String[OPEN_COLUMN];    	
    	
        for(int i=0;i < OPEN_COLUMN; i++){
        	columns[i] = csvDocument.getHeaders()[i];
        }
        OpenNode node = new OpenNode("username@"+openDocument.getName()+"_"+openDocument.getId());
    	LinkedList<String> row = new LinkedList<String>(Arrays.asList(columns));	
    	node.setRow(row);
    	node.setHeaderLine(1);
    	openNodeRepository.save(node);
    	
    	// Store Relationship 
        DocumentNodeRelationship drRel = 
        		template.createRelationshipBetween(openDocument,node,DocumentNodeRelationship.class, "INCLUDES",false);
        template.save(drRel);

    	// CONTENT
        while (csvDocument.readRecord()) {
        	// TODO:  username is dymanic
        	node = new OpenNode("username@"+openDocument.getName()+"_"+openDocument.getId());
        	row = new LinkedList<String>(Arrays.asList(csvDocument.getValues()));
        	//for(int i=0;i < OPEN_COLUMN; i++){
        	//	 row.add(csvDocument.get(column[i]));
            }//	
        	node.setRow(row);        	
        	openNodeRepository.save(node);        	
        	template.createRelationshipBetween(openDocument,node,DocumentNodeRelationship.class, "INCLUDES",false);
        	template.save(drRel);
        }
    }*/
    
    
    
    @Transactional
    public void importOpenData(CsvReader csvDocument, OpenDocument openDocument, User user) throws IOException {
    	
    	// 2) Import Open Data Content
        // HEADRERS
    	csvDocument.readHeaders();
    	int OPEN_COLUMN = csvDocument.getHeaders().length;
    	String[] column  = new String[OPEN_COLUMN];    	
    	
        for(int i=0;i < OPEN_COLUMN; i++){
        	column[i] = csvDocument.getHeaders()[i];
        }
        OpenNode node = new OpenNode(user.getLogin()+"@"+openDocument.getName()+"_id:"+openDocument.getId());
    	LinkedList<String> row = new LinkedList<String>(Arrays.asList(column));	
    	node.setRow(row);
    	node.setHeaderLine(1);
    	node.setName(openDocument.getName());
    	openNodeRepository.save(node);
    	
    	/* Store Relationship */
        DocumentNodeRelationship drRel = 
        		template.createRelationshipBetween(openDocument,node,DocumentNodeRelationship.class, "INCLUDES",false);
        //template.save(drRel);

    	// CONTENT
        while (csvDocument.readRecord()) {
        	// TODO:  username is dymanic
        	node = new OpenNode(user.getLogin()+"@"+openDocument.getName()+"_id:"+openDocument.getId());
        	node.setName(openDocument.getName());
        	row = new LinkedList<String>(Arrays.asList(csvDocument.getValues()));
        	/*for(int i=0;i < OPEN_COLUMN; i++){
        		 row.add(csvDocument.get(column[i]));
            }*/		
        	node.setRow(row);  
        	node.setHeaderLine(0);
        	openNodeRepository.save(node);
        	
        	/* Store Relationship */
        	template.createRelationshipBetween(openDocument,node,DocumentNodeRelationship.class, "INCLUDES",false);
        	//template.save(drRel);
        }
    }
    
    
    //@Transactional
    public void cleanDb() {
    	AbstractGraphDatabase  gs = ((AbstractGraphDatabase)template.getGraphDatabaseService()); 
    	Neo4jDatabaseCleaner cleaner = new Neo4jDatabaseCleaner(gs);
    	cleaner.cleanDb();
    }
    
}
