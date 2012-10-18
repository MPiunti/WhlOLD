package org.krams.dto;

import java.util.ArrayList;
import java.util.List;

import org.krams.domain.OpenNode;
import org.krams.domain.User;
import org.krams.dto.UserDto;

public class OpenNodeMapper {

	public static OpenNodeDto map(OpenNode openNode) {
		OpenNodeDto dto = new OpenNodeDto();
			dto.setId(openNode.getId());
			dto.setUnique(openNode.getUnique());
			dto.setName(openNode.getName());
			dto.setRow(openNode.getRow());
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
