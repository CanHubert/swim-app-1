package com.can.app.swim.swimapp.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;


@Getter
@Setter
@ToString
@MappedSuperclass
abstract public class UserBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    protected Long id;

    @NotBlank
    @Column(name = "first_name")
    protected String firstName;

    @NotBlank
    @Column(name = "last_name")
    protected String lastName;

    @Column(name = "date_of_birth")
    protected Date dateOfBirth;

}
