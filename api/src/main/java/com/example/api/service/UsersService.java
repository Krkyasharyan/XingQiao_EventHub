package com.example.api.service;

import com.example.api.DTO.UserDTO;
import com.example.api.DTO.EventAuthDTO;
import com.example.api.DTO.UserAuthDTO;
import java.util.List;

public interface UsersService {
    UserDTO createUser(UserAuthDTO userDTO);

    UserDTO getUserById(long id);

    List<UserDTO> getAllUsers();

    UserDTO updateUser(UserDTO userDTO);

    void deleteUser(long id);

    Long authenticate(UserAuthDTO user);

    UserAuthDTO getUserByIdAdmin(long id);

    List<EventAuthDTO> getParticipatedEvents(long id);

    List<EventAuthDTO> getOrganizedEvents(long id);
}
