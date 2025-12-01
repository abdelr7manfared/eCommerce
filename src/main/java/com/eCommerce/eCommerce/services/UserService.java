package com.eCommerce.eCommerce.services;

import com.eCommerce.eCommerce.dtos.UserDto;
import com.eCommerce.eCommerce.mappers.UserMapper;
import com.eCommerce.eCommerce.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;


@AllArgsConstructor
@Service
public class UserService {
    private final   UserRepository userRepository;
    private final UserMapper userMapper;

    public List<UserDto> getAllUsers(String sort) {
        if (!Set.of("name","email").contains(sort))
            sort = "name";
        return userRepository.findAll(Sort.by(sort).descending())
                .stream()
             //   .map(user -> new UserDto(user.getId(),user.getName(),user.getEmail()))
                 .map(userMapper::toDto)
                // user -> useMapper.toDto(user)
                .toList();
    }

    public ResponseEntity<UserDto> getUser(Long id) {
        var user =  userRepository.findById(id).orElse(null);
        if (user == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userMapper.toDto(user));

    }
}
