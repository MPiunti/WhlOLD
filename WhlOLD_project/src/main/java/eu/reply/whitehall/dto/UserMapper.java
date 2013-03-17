package eu.reply.whitehall.dto;

import java.util.ArrayList;
import java.util.List;


import eu.reply.whitehall.domain.nodes.User;
import eu.reply.whitehall.dto.UserDto;

public class UserMapper {

	public static UserDto map(User user) {
			UserDto dto = new UserDto();
			dto.setId(user.getId());
			dto.setFirstName(user.getName());
			dto.setLastName(user.getInfo());
			dto.setUsername(user.getLogin());
			//dto.setRole(user.getRole().getRole());
			return dto;
	}
	
	public static List<UserDto> map(List<User> users) {
		List<UserDto> dtos = new ArrayList<UserDto>();
		for (User user: users) {
			dtos.add(map(user));
		}
		return dtos;
	}
}
