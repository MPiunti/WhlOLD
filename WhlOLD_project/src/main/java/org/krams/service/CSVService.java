package org.krams.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;


import org.krams.domain.OpenDocument;
import org.krams.domain.OpenNode;
import org.krams.domain.User;
import org.krams.repository.OpenDocumentRepository;
import org.krams.repository.OpenNodeRepository;
import org.krams.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.csvreader.CsvReader;

@Service
public class CSVService {
	
	/* @Autowired 
	 private Neo4jTemplate template;*/
	 
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private OpenNodeRepository openNodeRepository;
	@Autowired
	private OpenDocumentRepository openDocumentRepository;
	 
    // private Map<Long,User> users = new HashMap<Long, User>();
	private final int MAX_COLUMN=30;
 
    @Transactional
    public void importRows(CsvReader csvDocument) throws IOException {
        // id; firstName; lastName; username; password;
    	
    	String[] column  = new String[MAX_COLUMN];
    	csvDocument.readHeaders();
        for(int i=0;i < csvDocument.getHeaders().length; i++){
        	column[i] = csvDocument.getHeaders()[i];
        }
        
        while (csvDocument.readRecord()) {
        	System.out.println("** " + csvDocument.get("firstName"));
        	User user = new User( //asLong( csvDocumente.get("id") ),
        			csvDocument.get(column[1]),
        			csvDocument.get(column[2]),
        			csvDocument.get(column[3]),
        			csvDocument.get(column[4]),
        			csvDocument.get(column[5]));
        	userRepository.save(user);
        	
           /* Station station = new Station(asShort(stationsFile,"id"),
                                          stationsFile.get("terminalName"),
                                          stationsFile.get("name"),
                                          asDouble(stationsFile, "lat"),
                                          asDouble(stationsFile, "lng"));
            template.save(station);
            stations.put(station.getStationId(), station);*/
        }
    }
    
    
    @Transactional
    public void importOpenRows(CsvReader csvDocument) throws IOException {
    	
        // HEADRERS
    	csvDocument.readHeaders();
    	int OPEN_COLUMN = csvDocument.getHeaders().length;
    	String[] column  = new String[OPEN_COLUMN];    	
    	
        for(int i=0;i < OPEN_COLUMN; i++){
        	column[i] = csvDocument.getHeaders()[i];
        }
        OpenNode node = new OpenNode("username@");
    	LinkedList<String> row = new LinkedList<String>(Arrays.asList(column));	
    	node.setRow(row);
    	node.setHeaderLine(1);
    	openNodeRepository.save(node);

    	// CONTENT
        while (csvDocument.readRecord()) {
        	// TODO:  username is dymanic
        	node = new OpenNode("username@");
        	row = new LinkedList<String>(Arrays.asList(csvDocument.getValues()));
        	/*for(int i=0;i < OPEN_COLUMN; i++){
        		 row.add(csvDocument.get(column[i]));
            }*/		
        	node.setRow(row);        	
        	openNodeRepository.save(node);
        }
    }
    
    
    
    @Transactional
    public void importOpenData(CsvReader csvDocument, String doc_name, Integer status) throws IOException {
    	// 1) Import Document
    	OpenDocument od = new OpenDocument();
    	// set indexed name of this document
    	od.setUser_id(1L);
    	od.setName(doc_name);
    	od.setVisible(status);
    	openDocumentRepository.save(od);
    	
    	// 2) Import Open Data Content
        // HEADRERS
    	csvDocument.readHeaders();
    	int OPEN_COLUMN = csvDocument.getHeaders().length;
    	String[] column  = new String[OPEN_COLUMN];    	
    	
        for(int i=0;i < OPEN_COLUMN; i++){
        	column[i] = csvDocument.getHeaders()[i];
        }
        OpenNode node = new OpenNode("username@");
    	LinkedList<String> row = new LinkedList<String>(Arrays.asList(column));	
    	node.setRow(row);
    	node.setHeaderLine(1);
    	node.setName(doc_name);
    	openNodeRepository.save(node);

    	// CONTENT
        while (csvDocument.readRecord()) {
        	// TODO:  username is dymanic
        	node = new OpenNode("username@");
        	node.setName(doc_name);
        	row = new LinkedList<String>(Arrays.asList(csvDocument.getValues()));
        	/*for(int i=0;i < OPEN_COLUMN; i++){
        		 row.add(csvDocument.get(column[i]));
            }*/		
        	node.setRow(row);  
        	node.setHeaderLine(0);
        	openNodeRepository.save(node);
        }
    }
    
}
