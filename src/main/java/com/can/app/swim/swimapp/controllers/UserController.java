package com.can.app.swim.swimapp.controllers;

import com.can.app.swim.swimapp.auth.payloads.responses.MessageResponse;
import com.can.app.swim.swimapp.dto.UserDto;
import com.can.app.swim.swimapp.entity.Role;
import com.can.app.swim.swimapp.entity.User;
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
@RequestMapping("/api/users")
public class UserController {

    private UserRepository userRepository;
    private RoleRepository roleRepository;

    @Autowired
    public UserController(UserRepository userRepository, RoleRepository roleRepository){
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @GetMapping("/details")
    public List<UserDto> getUsersWithRoles(){
      return userRepository.findAll().stream().map(UserDto::new).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public UserDto getUserDto(@PathVariable("id") long id){
      return userRepository.findById(id).map(UserDto::new)
              .orElseThrow(() ->new RuntimeException(String.format("User with id = %s doesn't exists", id)));
    }

    @PutMapping("/{userId}/roles/{roleId}")
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
}


