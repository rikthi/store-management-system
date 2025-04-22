package dev.rikthipranadhik.storemanagementsystembackend.mapper.user.impl;

import dev.rikthipranadhik.storemanagementsystembackend.dto.user.UserDTO;
import dev.rikthipranadhik.storemanagementsystembackend.entity.user.User;
import dev.rikthipranadhik.storemanagementsystembackend.mapper.user.UserMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User fromDTO(UserDTO userDTO) {
        return new User(
                userDTO.id(),
                userDTO.email(),
                userDTO.password(),
                null // set employee later in service
        );
    }


    @Override
    public UserDTO toDTO(User user) {
        return new  UserDTO(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                user.getEmployee() != null ? user.getEmployee().getId() : null
        );
    }
}
