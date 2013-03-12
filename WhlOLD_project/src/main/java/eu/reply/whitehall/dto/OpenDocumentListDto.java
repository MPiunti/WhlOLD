package eu.reply.whitehall.dto;

import java.util.List;

public class OpenDocumentListDto {

	private List<OpenDocumentDto> openDocuments;

	public List<OpenDocumentDto> getOpenDocuments() {
		return openDocuments;
	}

	public void setOpenDocuments(List<OpenDocumentDto> openDoc) {
		this.openDocuments = openDoc;
	}
	
	public int size() {
		return openDocuments.size();
	}

}
