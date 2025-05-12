package com.zosh.mapper;

import com.zosh.dto.UserDTO;
import com.zosh.modal.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {

    public static UserDTO toDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setEmail(user.getEmail());
        userDTO.setRole(user.getRole());
        userDTO.setMobile(user.getMobile());
        userDTO.setCreatedAt(user.getCreatedAt());
        // Assuming addresses is a list of strings for simplicity, map addresses if needed
        userDTO.setAddresses(user.getAddresses().stream().map(address -> address.toString()).collect(Collectors.toList()));

        return userDTO;
    }

    public static User toEntity(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setRole(userDTO.getRole());
        user.setMobile(userDTO.getMobile());
        // Handle setting addresses if needed
        return user;
    }
}
