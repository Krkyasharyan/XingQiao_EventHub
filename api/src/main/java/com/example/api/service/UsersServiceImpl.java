package com.example.api.service;

import com.example.api.model.Users;
import com.example.api.repository.UsersRepository;
//import com.example.api.service.UserssService;

import lombok.AllArgsConstructor;

//import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;
//import org.springframework.util.StringUtils;

import java.util.List;
import org.modelmapper.ModelMapper;

import com.example.api.DTO.UserDTO;
import java.util.stream.Collectors;

import com.example.api.DTO.EventAuthDTO;
import com.example.api.DTO.UserAuthDTO;



@Service
@AllArgsConstructor
public class UsersServiceImpl implements UsersService {
    
    private UsersRepository usersRepository;
    private ModelMapper modelMapper;

    @Override
    public UserDTO createUser(UserAuthDTO userAuthDTO) {
        Users user = modelMapper.map(userAuthDTO, Users.class);
        Users savedUser = usersRepository.save(user);
        return modelMapper.map(savedUser, UserDTO.class);
    }

    @Override
    public UserDTO getUserById(long id) {
        Users user = usersRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return modelMapper.map(user, UserDTO.class);
    }

    @Override
    public UserAuthDTO getUserByIdAdmin(long id){
        Users user = usersRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return modelMapper.map(user, UserAuthDTO.class);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<Users> users = usersRepository.findAll();
        return users.stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Long authenticate(UserAuthDTO user) {
        Users existingUser = usersRepository.findByUsername(user.getUsername());
        if (existingUser != null && existingUser.getPassword().equals(user.getPassword())) {
            return existingUser.getId();
        } else {
            return null;
        }
    }

    @Override
    public List<EventAuthDTO> getParticipatedEvents(long id){
        Users existingUser = usersRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return existingUser.getParticipatedEvents().stream() // getParticipatedEvents() is a method in Users.java
                .map(event -> modelMapper.map(event, EventAuthDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<EventAuthDTO> getOrganizedEvents(long id){
        Users existingUser = usersRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return existingUser.getOrganizedEvents().stream() // getOrganizedEvents() is a method in Users.java
                .map(event -> modelMapper.map(event, EventAuthDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO updateUser(UserDTO userDTO) {
        Users existingUser = usersRepository.findById(userDTO.getId()).orElseThrow(() -> new RuntimeException("User not found"));
        
        existingUser.setUsername(null != userDTO.getUsername() ? userDTO.getUsername() : existingUser.getUsername());
        existingUser.setEmail(null != userDTO.getEmail() ? userDTO.getEmail() : existingUser.getEmail());

        Users updatedUser = usersRepository.save(existingUser);
        return modelMapper.map(updatedUser, UserDTO.class);
    }

    @Override
    public void deleteUser(long id) {
        usersRepository.deleteById(id);
    }
}
