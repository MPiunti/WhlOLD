package eu.reply.whitehall.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.conversion.EndResult;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.stereotype.Service;

import eu.reply.whitehall.domain.nodes.OpenDocument;
import eu.reply.whitehall.domain.nodes.User;
import eu.reply.whitehall.domain.relationships.UserDocumentRelationship;
import eu.reply.whitehall.repository.OpenDocumentRepository;
import eu.reply.whitehall.repository.UserRepository;


@Service
public class OpenDocumentService {
	
	@Autowired 
	private Neo4jTemplate template;
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private OpenDocumentRepository openDocumentRepository;

	
	public List<OpenDocument> findByUser() {
		List<OpenDocument> openDocuments = new ArrayList<OpenDocument>();
		
		Iterable<OpenDocument> results = openDocumentRepository.findByOwnersLogin(userRepository.getUserFromSession().getLogin());
		for (OpenDocument r: results) {
			System.out.println("result retrieved doc_: " + r.getName() );
			openDocuments.add(r);
		}
		
		return openDocuments;
    }
	
	
	/*public List<OpenDocument> readAll() {
		List<OpenDocument> openDocuments = new ArrayList<OpenDocument>();
		
		EndResult<OpenDocument> results = openDocumentRepository.findAll();
		for (OpenDocument r: results) {
			System.out.println("results: " + results );
			openDocuments.add(r);
		}
		
		return openDocuments;
	}*/
	
	
	
	public List<OpenDocument> findAllbyPropertyValue(String pName, Object obj) {
		List<OpenDocument> openDocuments = new ArrayList<OpenDocument>();
		
		EndResult<OpenDocument> results = openDocumentRepository.findAllByPropertyValue(pName,obj);
		for (OpenDocument r: results) {
			openDocuments.add(r);
		}
		
		return openDocuments;
	}
	
	
	public OpenDocument create(OpenDocument openDocument, User user) {
		OpenDocument existingDocument = openDocumentRepository.findByPropertyValue("name",openDocument.getName());	
		if (existingDocument != null) {
			throw new RuntimeException("Document already exists!");
		}		
		OpenDocument od = openDocumentRepository.save(openDocument);		
        UserDocumentRelationship t = template.createRelationshipBetween(user, openDocument, UserDocumentRelationship.class, "OWNS", false);	
        template.save(t);

		return od;
	}
	
	
	/**
	 * quit all the nodes
	 */
	public boolean quit() {	
		openDocumentRepository.deleteAll();
		return true;
	}
}
