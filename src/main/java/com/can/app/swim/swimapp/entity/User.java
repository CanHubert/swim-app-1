package com.can.app.swim.swimapp.entity;

import com.can.app.swim.swimapp.auth.payloads.requests.SignupRequest;
import com.can.app.swim.swimapp.dto.UserDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.BatchSize;
import org.springframework.lang.Nullable;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.List;


@Entity
@Getter
@Setter
@ToString
@Table(name="user",
		uniqueConstraints = {
		@UniqueConstraint(columnNames = "username"),
		@UniqueConstraint(columnNames = "email")
})
public class User extends UserBase{

    @NotBlank
    @Column(name = "username")
    private String username;
    
    @NotBlank
    @Column(name = "password")
	private String password;

	@Email
	@Column(name = "email")
	private String email;

	@Nullable
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "parent")
    @BatchSize(size = 5)
	private List<Children> childrens;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    @BatchSize(size = 4)
    private Collection<Role> roles;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_country",
                joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
                inverseJoinColumns = @JoinColumn(name = "country_id", referencedColumnName = "id"))
    @BatchSize(size = 2)
    private Collection<Country> countries;

//    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JoinColumn(name = "countryId")
//    private Country country;

    public User() {
    }

    public User(String username, String firstName, String lastName, String email, String password) {
        this(username, email, password);
    	this.firstName = firstName;
        this.lastName = lastName;
    }

    public User(String username, String firstName, String lastName, String email, String password, Collection<Role> roles) {
        this(username,firstName,lastName,email,password);
        this.roles = roles;
    }
    
    public User(String username, String email, String password) {
		this.username = username;
		this.email = email;
		this.password = password;
	}

	public User(UserDto dto){
        this.id = dto.getId();
        this.firstName = dto.getFirstName();
        this.lastName = dto.getLastName();
        this.username = dto.getUsername();
        this.email = dto.getEmail();
        this.password = dto.getPassword();
    }

	public User(SignupRequest signupRequest, PasswordEncoder encoder){
        this.username = signupRequest.getUsername();
        this.firstName = signupRequest.getFirstName();
        this.lastName = signupRequest.getLastName();
        this.email = signupRequest.getEmail();
        this.password = encoder.encode(signupRequest.getPassword());
    }

    public User(String firstName, String lastName, String email, boolean test){
        this.firstName = firstName;
        this.lastName= lastName;
        this.email= email;
    }

    @JsonIgnore
    public String getFullName() {
        return firstName + " " + lastName;
    }
}
