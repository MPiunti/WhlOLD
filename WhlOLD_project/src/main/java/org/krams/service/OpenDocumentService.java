package org.krams.service;

import java.util.ArrayList;
import java.util.List;

import org.krams.domain.OpenDocument;
import org.krams.domain.OpenNode;
import org.krams.domain.User;
import org.krams.repository.OpenDocumentRepository;
import org.krams.repository.OpenNodeRepository;
import org.krams.repository.RoleRepository;
import org.krams.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.conversion.EndResult;
import org.springframework.stereotype.Service;

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
