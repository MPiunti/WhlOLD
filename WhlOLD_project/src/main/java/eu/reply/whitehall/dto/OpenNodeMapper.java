package eu.reply.whitehall.dto;

import java.util.ArrayList;
import java.util.List;


import eu.reply.whitehall.domain.nodes.OpenNode;
import eu.reply.whitehall.domain.nodes.User;
import eu.reply.whitehall.dto.UserDto;

public class OpenNodeMapper {

	public static OpenNodeDto map(OpenNode openNode) {
		OpenNodeDto dto = new OpenNodeDto();
			dto.setId(openNode.getId());
			dto.setUnique(openNode.getUnique());
			dto.setName(openNode.getName());
			dto.setType(openNode.getType());
			dto.setRow(openNode.getRow());
			
			/* inner relationships */
			dto.setDocuments(openNode.getDocuments());
			dto.setVenues(openNode.getVenues());
			/* outher relationships */
			dto.setdBPediaLinks(openNode.getdBPediaLinks());
			
			return dto;
	}
	
	
	public static List<OpenNodeDto> map(List<OpenNode> openNodes) {
		List<OpenNodeDto> dtos = new ArrayList<OpenNodeDto>();
		for (OpenNode node: openNodes) {
			dtos.add(map(node));
		}
		return dtos;
	}
}
