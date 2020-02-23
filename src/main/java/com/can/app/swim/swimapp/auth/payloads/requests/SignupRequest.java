package com.can.app.swim.swimapp.auth.payloads.requests;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter @Setter
public class SignupRequest {
	@NotBlank
    @Size(min = 3, max = 32)
    private String username;
	
	@NotBlank
    @Size(min = 2, max = 32)
	private String firstName;
	
	@NotBlank
    @Size(min = 2, max = 32)
	private String lastName;
 
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;
    
    private Set<String> role;
    
    @NotBlank
    @Size(min = 6, max = 40)
    private String password;

}
