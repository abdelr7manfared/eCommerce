package com.eCommerce.eCommerce.services;

import com.eCommerce.eCommerce.dtos.requests.ChangePasswordRequest;
import com.eCommerce.eCommerce.dtos.requests.RegisterUserRequest;
import com.eCommerce.eCommerce.dtos.requests.UpdateUserRequest;
import com.eCommerce.eCommerce.dtos.responses.UserDto;
import com.eCommerce.eCommerce.mappers.UserMapper;
import com.eCommerce.eCommerce.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;
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

    public ResponseEntity<?> registerUser(RegisterUserRequest request, UriComponentsBuilder uriBuilder) {
        if (userRepository.existsByEmail(request.getEmail())){
            return ResponseEntity.badRequest().body(
                    Map.of("email","Email already Registered.")
            );
        }
        var user = userMapper.toEntity(request);
        userRepository.save(user);
        var userDto = userMapper.toDto(user);
        var uri = uriBuilder.path("/users/{id}").buildAndExpand(userDto.getId()).toUri();
        return ResponseEntity.created(uri).body(userDto);
    }


    public ResponseEntity<UserDto> updateUser(UpdateUserRequest request, Long id) {
        var user = userRepository.findById(id).orElse(null);
        if (user == null){
            return ResponseEntity.notFound().build();
        }
        userMapper.update(request,user);
        userRepository.save(user);
        return ResponseEntity.ok(userMapper.toDto(user));
    }

    public ResponseEntity<Void> deleteUser(Long id) {
        var user = userRepository.findById(id).orElse(null);
        if (user == null){
            return ResponseEntity.notFound().build();
        }
        userRepository.delete(user);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<Void> changePassword(Long id, ChangePasswordRequest request) {
        var user = userRepository.findById(id).orElse(null);
        if (user == null){
            return ResponseEntity.notFound().build();
        }
        if (!user.getPassword().equals(request.getOldPassword())){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        user.setPassword(request.getNewPassword());
        userRepository.save(user);
        return ResponseEntity.noContent().build();

    }
}
