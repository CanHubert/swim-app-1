package com.can.app.swim.swimapp.dto;

import com.can.app.swim.swimapp.entity.Country;
import com.can.app.swim.swimapp.entity.Role;
import com.can.app.swim.swimapp.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter
public class UserDto {

    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private List<String> roles;
    private List<String> countries;

    public UserDto(User user){
        this.id=user.getId();
        this.username = user.getUsername();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.roles = user.getRoles()
                .stream()
                .map(role -> role.getName().name())
                .sorted()
                .collect(Collectors.toList());
         this.countries = user.getCountries()
                 .stream()
                 .map(Country::getName)
                 .sorted()
                 .collect(Collectors.toList());
    }


}
