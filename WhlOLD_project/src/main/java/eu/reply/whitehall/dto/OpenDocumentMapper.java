package eu.reply.whitehall.dto;

import java.util.ArrayList;
import java.util.List;

import eu.reply.whitehall.domain.nodes.OpenDocument;


public class OpenDocumentMapper {

	public static OpenDocumentDto map(OpenDocument openDoc) {
		OpenDocumentDto dto = new OpenDocumentDto();
			dto.setId(openDoc.getId());			
			dto.setName(openDoc.getName());
			//dto.setUser_id(openDoc.getUser_id());
			dto.setVisible(openDoc.getVisible());
			return dto;
	}
	
	
	public static List<OpenDocumentDto> map(List<OpenDocument> openDocuments) {
		List<OpenDocumentDto> dtos = new ArrayList<OpenDocumentDto>();
		for (OpenDocument node: openDocuments) {
			dtos.add(map(node));
		}
		return dtos;
	}
}
