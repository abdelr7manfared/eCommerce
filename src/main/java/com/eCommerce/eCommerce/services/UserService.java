package com.eCommerce.eCommerce.services;

import com.eCommerce.eCommerce.dtos.UserDto;
import com.eCommerce.eCommerce.entities.User;
import com.eCommerce.eCommerce.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(user -> new UserDto(user.getId(),user.getName(),user.getEmail()))
                .toList();
    }

    public ResponseEntity<UserDto> getUser(Long id) {
        var user =  userRepository.findById(id).orElse(null);
        if (user == null){
            return ResponseEntity.notFound().build();
        }
        UserDto userDto = new UserDto(user.getId(),user.getName(),user.getEmail());
        return ResponseEntity.ok(userDto);

    }
}
