package com.eCommerce.eCommerce.controllers;

import com.eCommerce.eCommerce.dtos.UserDto;
import com.eCommerce.eCommerce.entities.User;
import com.eCommerce.eCommerce.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
   @Autowired
   private UserService userService;
    @GetMapping
    public List<UserDto> getAllUsers(){
        return userService.getAllUsers();
    }
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id){
        return userService.getUser(id);
    }
}
