package ru.ostap.userservice.util;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.ostap.userservice.dto.UserDTO;
import ru.ostap.userservice.models.User;

@Component
@RequiredArgsConstructor
public class DtoConverter {
    private final ModelMapper modelMapper;

    public User convertToUser(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }
}
