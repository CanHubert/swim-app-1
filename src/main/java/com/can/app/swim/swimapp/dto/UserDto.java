package com.can.app.swim.swimapp.dto;

import com.can.app.swim.swimapp.entity.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter
public class UserDto {

    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Date dateOfBirth;
    private List<Role> roles;
    private List<Country> countries;
    private List<Children> childrens;

    public UserDto(){}

    public UserDto(User user){
        this.id=user.getId();
        this.username = user.getUsername();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.dateOfBirth = user.getDateOfBirth();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.roles = user.getRoles()
                .stream()
                .sorted(Comparator.comparing(Role::getOrder))
                .collect(Collectors.toList());
         this.countries = (List<Country>) user.getCountries();
         this.childrens = user.getChildrens();
    }
}
