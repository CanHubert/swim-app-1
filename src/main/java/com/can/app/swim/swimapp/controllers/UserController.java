package com.can.app.swim.swimapp.controllers;

import com.can.app.swim.swimapp.dto.UserDto;
import com.can.app.swim.swimapp.entity.User;
import com.can.app.swim.swimapp.repository.CountryRepository;
import com.can.app.swim.swimapp.repository.RoleRepository;
import com.can.app.swim.swimapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import java.util.stream.Collectors;

import static com.can.app.swim.swimapp.helpers.ExceptionsUtil.throwRuntimeException;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class UserController {

    private UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @GetMapping("/users/details")
    public List<UserDto> getUsersWithRoles(){
      return userRepository.findAll().stream().map(UserDto::new).collect(Collectors.toList());
    }

    @GetMapping("/users/{id}")
    public UserDto getUserDto(@PathVariable("id") long id){
      return userRepository.findById(id).map(UserDto::new)
              .orElseThrow(throwRuntimeException(String.format("User with id = %s doesn't exists", id)));
    }

    @PutMapping("/users")
    public User updateUser(@RequestBody UserDto user){
        User u = dtoToEntity(user);
        userRepository.save(u);
        return u;
    }

    private User dtoToEntity(UserDto dto){
        User user = new User(dto);
        user.setRoles((dto.getRoles()));
        user.setCountries(dto.getCountries());
        return user;
    }
}


