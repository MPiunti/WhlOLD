package eu.reply.whitehall.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.conversion.EndResult;
import org.springframework.stereotype.Service;

import eu.reply.whitehall.domain.OpenDocument;
import eu.reply.whitehall.domain.OpenNode;
import eu.reply.whitehall.domain.User;
import eu.reply.whitehall.repository.OpenDocumentRepository;
import eu.reply.whitehall.repository.OpenNodeRepository;
import eu.reply.whitehall.repository.RoleRepository;
import eu.reply.whitehall.repository.UserRepository;

@Service
public class OpenDocumentService {

	@Autowired
	private OpenDocumentRepository openDocumentRepository;

	
	public List<OpenDocument> readAll() {
		List<OpenDocument> openDocuments = new ArrayList<OpenDocument>();
		
		EndResult<OpenDocument> results = openDocumentRepository.findAll();
		for (OpenDocument r: results) {
			System.out.println("results: " + results );
			openDocuments.add(r);
		}
		
		return openDocuments;
	}
	
	
	
	public List<OpenDocument> findAllbyPropertyValue(String pName, Object obj) {
		List<OpenDocument> openDocuments = new ArrayList<OpenDocument>();
		
		EndResult<OpenDocument> results = openDocumentRepository.findAllByPropertyValue(pName,obj);
		for (OpenDocument r: results) {
			openDocuments.add(r);
		}
		
		return openDocuments;
	}
	
	
	/**
	 * quit all the nodes
	 */
	public boolean quit() {	
		openDocumentRepository.deleteAll();
		return true;
	}
}
