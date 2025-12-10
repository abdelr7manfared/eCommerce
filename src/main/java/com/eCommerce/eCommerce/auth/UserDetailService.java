package com.eCommerce.eCommerce.auth;

import com.eCommerce.eCommerce.users.EmailNotFoundException;
import com.eCommerce.eCommerce.users.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@AllArgsConstructor
@Service
public class UserDetailService implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var user = userRepository.findByEmail(email).orElseThrow(EmailNotFoundException::new);
        return new User(user.getEmail(),user.getPassword(), Collections.emptyList());
    }
}
