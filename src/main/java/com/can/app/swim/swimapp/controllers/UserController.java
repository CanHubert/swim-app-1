package com.can.app.swim.swimapp.controllers;

import com.can.app.swim.swimapp.auth.payloads.responses.MessageResponse;
import com.can.app.swim.swimapp.dto.UserDto;
import com.can.app.swim.swimapp.entity.Country;
import com.can.app.swim.swimapp.entity.Role;
import com.can.app.swim.swimapp.entity.User;
import com.can.app.swim.swimapp.enums.EnumRole;
import com.can.app.swim.swimapp.repository.CountryRepository;
import com.can.app.swim.swimapp.repository.RoleRepository;
import com.can.app.swim.swimapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class UserController {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private CountryRepository countryRepository;

    @Autowired
    public UserController(UserRepository userRepository, RoleRepository roleRepository, CountryRepository countryRepository){
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.countryRepository = countryRepository;
    }

    @GetMapping("/users/details")
    public List<UserDto> getUsersWithRoles(){
      return userRepository.findAll().stream().map(UserDto::new).collect(Collectors.toList());
    }

    @GetMapping("/users/{id}")
    public UserDto getUserDto(@PathVariable("id") long id){
      return userRepository.findById(id).map(UserDto::new)
              .orElseThrow(() ->new RuntimeException(String.format("User with id = %s doesn't exists", id)));
    }

    @PutMapping("/users")
    public User updateUser(@RequestBody UserDto user){
        User u = dtoToEntity(user);
        userRepository.save(u);
        return u;
    }

    @PutMapping("/users/{userId}/roles/{roleId}")
    public ResponseEntity<?> addUserRole(@PathVariable("userId") long userId, @PathVariable("roleId") long roleId){
        Optional<User> $user = userRepository.findById(userId);
        User user = null;
        if($user.isPresent())
        {
            Optional<Role> $role = roleRepository.findById(roleId);
            if($role.isPresent())
            {
                user = $user.get();
                Role role = $role.get();
                if(user.getRoles().contains(role))
                {
                    return ResponseEntity.badRequest().body(new MessageResponse("User already have this role!"));
                }
                user.getRoles().add($role.get());
                userRepository.save(user);
            }
            else
            {
               // throw new RuntimeException("no user with given id");
                return ResponseEntity.badRequest().body(new MessageResponse(String.format("Role with id = %s doesn't exists!", roleId)));
            }
        }
        else
        {
           // throw new RuntimeException("no user with given id");
            return ResponseEntity.badRequest().body(new MessageResponse(String.format("User with id = %s doesn't exists!", userId)));
        }

        return ResponseEntity.ok(user);
    }

    private User dtoToEntity(UserDto dto){
        User user = new User(dto);
        user.setRoles((dto.getRoles()));
        user.setCountries(countryRepository.findByNameIn(dto.getCountries()));
        return user;
    }
}


