package com.example.api.controller;

import com.example.api.dto.UserResponseDTO;
import com.example.api.model.User;
import com.example.api.repository.UsersRepository;
import com.example.api.service.UsersService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.modelmapper.ModelMapper;
//import java.util.stream.Collectors;


@RestController
@AllArgsConstructor
@RequestMapping("api/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UsersController {

    private UsersService usersService;
    private UsersRepository usersRepository;
    private ModelMapper modelMapper;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody User user) {
        User createdUser = usersService.createUser(user);
        return new ResponseEntity<>(modelMapper.map(createdUser, UserResponseDTO.class), HttpStatus.CREATED);
    }

     @PutMapping
    public ResponseEntity<UserResponseDTO> makeAdmin(@PathVariable Long userId){
        User existingUser = usersRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        existingUser.setIsAdmin(true);
        usersRepository.save(existingUser);
        return new ResponseEntity<>(modelMapper.map(existingUser, UserResponseDTO.class), HttpStatus.CREATED);
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserResponseDTO> getUserByUsername(@PathVariable String username) {
        User existingUser = usersService.findByUsername(username);
        if (existingUser != null)
            return new ResponseEntity<>(modelMapper.map(existingUser, UserResponseDTO.class), HttpStatus.CREATED);
        else
            return new ResponseEntity<>(modelMapper.map(existingUser, UserResponseDTO.class), HttpStatus.CREATED);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<User> getUserById(@PathVariable long id) {
        User user = usersService.findUserById(id);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = usersService.findAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        user.setId(id);
        User updatedUser = usersService.updateUser(user);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponseDTO> authenticate(@RequestBody User user) {
    Long userId = usersService.authenticate(user);
    if (userId != null) {
        UserResponseDTO responseDTO = new UserResponseDTO();
        responseDTO.setUserId(userId.intValue());
        responseDTO.setUsername(user.getUsername());
        return ResponseEntity.ok(responseDTO);
    } else {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") long id) {
        usersService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
