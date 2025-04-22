package dev.rikthipranadhik.storemanagementsystembackend.mapper.user;

import dev.rikthipranadhik.storemanagementsystembackend.dto.user.UserDTO;
import dev.rikthipranadhik.storemanagementsystembackend.entity.user.User;

public interface UserMapper {
    User fromDTO(UserDTO userDTO);
    UserDTO toDTO(User user);
}
