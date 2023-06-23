package com.example.api.service;


import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;


import com.example.api.repository.UsersRepository;
import com.example.api.model.User;
import java.util.List;

@Service
@AllArgsConstructor
public class UsersServiceImpl implements UsersService {
    
    private UsersRepository usersRepository;

    @Override
    @Transactional
    public User createUser(User user) {
        return usersRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return usersRepository.findByUsername(username);
    }

    @Override
    public User findUserById(long id){
        return usersRepository.findById(id).get();
    }

    @Override
    public List<User> findAllUsers() {
        return usersRepository.findAll();
    }

    @Override
    public User updateUser(User user) {
        User existingUser = usersRepository.findById(user.getId()).get();

        existingUser.setUsername(null != user.getUsername() ? user.getUsername() : existingUser.getUsername());
        existingUser.setPassword(null != user.getPassword() ? user.getPassword() : existingUser.getPassword());
        existingUser.setEmail(null != user.getEmail() ? user.getEmail() : existingUser.getEmail());
        existingUser.setAddress(null != user.getAddress() ? user.getAddress() : existingUser.getAddress());
        existingUser.setPhoneNumber(null != user.getPhoneNumber() ? user.getPhoneNumber() : existingUser.getPhoneNumber());

        User updateUser = usersRepository.save(existingUser);

        return updateUser;
    }

    @Override
    public Long authenticate(User user) {
        User existingUser = usersRepository.findByUsername(user.getUsername());
        if (existingUser == null) {
            return null;
        } else if (existingUser.getPassword().equals(user.getPassword())) {
            return existingUser.getId();
        } 
        else {
            return null;
        }
    }
    
    @Override
    public void deleteUser(long id) {
        usersRepository.deleteById(id);
    }

}
