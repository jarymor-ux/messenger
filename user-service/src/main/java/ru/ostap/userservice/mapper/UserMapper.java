package ru.ostap.userservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.ostap.userservice.dto.UserDTO;
import ru.ostap.userservice.models.User;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO toUserDTO(User user);

    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "registrationAt", ignore = true)
    @Mapping(target = "onlineStatus", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "enabled", ignore = true)
    User toUser(UserDTO userDTO);

    List<UserDTO> toUserDTOs(List<User> users);
}
