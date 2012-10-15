package org.krams.service;

import java.io.IOException;


import org.krams.domain.User;
import org.krams.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.csvreader.CsvReader;

@Service
public class CSVService {
	
	/* @Autowired 
	 private Neo4jTemplate template;*/
	 
	@Autowired
	private UserRepository userRepository;
	 
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
    
    
    
    
    private Long asLong(Object obj) throws Exception{
    	return new Long(obj.toString());
    }
}
