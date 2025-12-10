package com.eCommerce.eCommerce.users;

import com.eCommerce.eCommerce.auth.ChangePasswordRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;


@AllArgsConstructor
@Service
public class UserService {
    private final   UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder encoder;
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
        var user =  userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        return userMapper.toDto(user);

    }

    public UserDto registerUser(RegisterUserRequest request) {
        userRepository.existsByEmail(request.getEmail()).orElseThrow(EmailAlreadyExistsException::new);

        request.setPassword(encoder.encode(request.getPassword()));
        var user = userMapper.toEntity(request);

        user.setRole(Role.USER);

        userRepository.save(user);
        return userMapper.toDto(user);
    }


    public UserDto updateUser(UpdateUserRequest request, Long id) {
        var user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);

        userMapper.update(request,user);
        userRepository.save(user);
        return userMapper.toDto(user);
    }

    public void deleteUser(Long id) {
        var user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);

        userRepository.delete(user);
    }

    public void changePassword(Long id, ChangePasswordRequest request) {
        var user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);

        if (!user.getPassword().equals(request.getOldPassword())){
            throw new InvalidOldPasswordException();
        }
        user.setPassword(request.getNewPassword());
        userRepository.save(user);

    }
}
