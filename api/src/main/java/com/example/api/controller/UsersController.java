package com.example.api.controller;

import com.example.api.service.UsersService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import com.example.api.DTO.UserDTO;
import com.example.api.DTO.EventAuthDTO;
import com.example.api.DTO.UserAuthDTO;


@RestController
@AllArgsConstructor
@RequestMapping("api/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UsersController {
    
    private UsersService usersService;

    @PostMapping
    public ResponseEntity<UserDTO> createUsers(@RequestBody UserAuthDTO userAuthDTO) {
        UserDTO newUserDTO = usersService.createUser(userAuthDTO);
        return new ResponseEntity<>(newUserDTO, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> usersDTO = usersService.getAllUsers();
        return new ResponseEntity<>(usersDTO, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable("id") long id) {
        UserDTO userDTO = usersService.getUserById(id);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @GetMapping("/admin/{id}")
    public ResponseEntity<UserAuthDTO> getUserByIdAdmin(@PathVariable("id") long id) {
        UserAuthDTO user = usersService.getUserByIdAdmin(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUsers(@PathVariable("id") long id, @RequestBody UserDTO userDTO) {
        userDTO.setId(id);
        UserDTO updatedUserDTO = usersService.updateUser(userDTO);
        return new ResponseEntity<>(updatedUserDTO, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<UserDTO> authenticate(@RequestBody UserAuthDTO user) {
        Long userId = usersService.authenticate(user);
        UserDTO existingUser = usersService.getUserById(userId);
        if (userId != null) {
            UserDTO responseDTO = new UserDTO();
            responseDTO.setId(userId.intValue());
            responseDTO.setUsername(user.getUsername());
            responseDTO.setEmail(existingUser.getEmail());
            return ResponseEntity.ok(responseDTO);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/participatedEvents/{id}")
    public ResponseEntity<List<EventAuthDTO>> getParticipatedEvents(@PathVariable long id) {
        List<EventAuthDTO> events = usersService.getParticipatedEvents(id);
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    @GetMapping("/organizedEvents/{id}")
    public ResponseEntity<List<EventAuthDTO>> getOrganizedEvents(@PathVariable long id) {
        List<EventAuthDTO> events = usersService.getOrganizedEvents(id);
        return new ResponseEntity<>(events, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUsers(@PathVariable("id") long id) {
        usersService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
