package com.eCommerce.eCommerce.services;

import com.eCommerce.eCommerce.dtos.requests.ChangePasswordRequest;
import com.eCommerce.eCommerce.dtos.requests.RegisterUserRequest;
import com.eCommerce.eCommerce.dtos.requests.UpdateUserRequest;
import com.eCommerce.eCommerce.dtos.responses.UserDto;
import com.eCommerce.eCommerce.exceptions.EmailAlreadyExistsException;
import com.eCommerce.eCommerce.exceptions.InvalidOldPasswordException;
import com.eCommerce.eCommerce.exceptions.UserNotFoundException;
import com.eCommerce.eCommerce.mappers.UserMapper;
import com.eCommerce.eCommerce.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URISyntaxException;
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

    public UserDto getUser(Long id) {
        var user =  userRepository.findById(id).orElse(null);
        if (user == null){
            throw new UserNotFoundException();
        }
        return userMapper.toDto(user);

    }

    public UserDto registerUser(RegisterUserRequest request) {
        if (userRepository.existsByEmail(request.getEmail())){
            throw new EmailAlreadyExistsException();
        }
        var user = userMapper.toEntity(request);
        userRepository.save(user);
        return   userMapper.toDto(user);
    }


    public UserDto updateUser(UpdateUserRequest request, Long id) {
        var user = userRepository.findById(id).orElse(null);
        if (user == null){
            throw new UserNotFoundException();
        }
        userMapper.update(request,user);
        userRepository.save(user);
        return userMapper.toDto(user);
    }

    public void deleteUser(Long id) {
        var user = userRepository.findById(id).orElse(null);
        if (user == null){
            throw new UserNotFoundException();
        }
        userRepository.delete(user);
    }

    public void changePassword(Long id, ChangePasswordRequest request) {
        var user = userRepository.findById(id).orElse(null);
        if (user == null){
            throw new UserNotFoundException();
        }
        if (!user.getPassword().equals(request.getOldPassword())){
            throw new InvalidOldPasswordException();
        }
        user.setPassword(request.getNewPassword());
        userRepository.save(user);

    }
}
