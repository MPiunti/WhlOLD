package eu.reply.whitehall.dto;

import java.util.List;

public class OpenNodeListDto {

	private List<OpenNodeDto> openNodes;

	public List<OpenNodeDto> getOpenNodes() {
		return openNodes;
	}

	public void setOpenNodes(List<OpenNodeDto> openNodes) {
		this.openNodes = openNodes;
	}
	
	public int size() {
		return openNodes.size();
	}

}
