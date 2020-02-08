package com.can.app.swim.swimapp.entity;

import com.can.app.swim.swimapp.auth.payloads.requests.SignupRequest;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Collection;


@Entity
@Getter
@Setter
@Table(name="user",
		uniqueConstraints = {
		@UniqueConstraint(columnNames = "username"),
		@UniqueConstraint(columnNames = "email")
})
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    @Column(name = "username")
    private String username;
    
    @NotBlank
    @Column(name = "password")
	private String password;

    @NotBlank
	@Column(name = "first_name")
	private String firstName;
    
    @NotBlank
	@Column(name = "last_name")
	private String lastName;

	@Email
	@Column(name = "email")
	private String email;

	@JsonManagedReference
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private Collection<Role> roles;

//    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JoinTable(name = "user_country",
//                joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
//                inverseJoinColumns = @JoinColumn(name = "country_id", referencedColumnName = "id"))
//    private Collection<Country> countries;

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

	public User(SignupRequest signupRequest, PasswordEncoder encoder){
        this.username = signupRequest.getUsername();
        this.firstName = signupRequest.getFirstName();
        this.lastName = signupRequest.getLastName();
        this.email = signupRequest.getEmail();
        this.password = encoder.encode(signupRequest.getPassword());
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", roles=" + roles +
                '}';
    }

    @JsonIgnore
    public String getFullName() {
        return firstName + " " + lastName;
    }
}
