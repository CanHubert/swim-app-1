package com.can.app.swim.swimapp.auth.payloads.responses;

import com.can.app.swim.swimapp.auth.services.UserDetailsImpl;
import com.can.app.swim.swimapp.entity.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class JwtResponse {
	private String token;
	private String type = "Bearer";
	private Long id;
	private String username;
	private String email;
	private List<Role> roles;

	public JwtResponse(String accessToken, UserDetailsImpl userDetails, List<Role> roles) {
		this.token = accessToken;
		this.id = userDetails.getId();
		this.username = userDetails.getUsername();
		this.email = userDetails.getEmail();
		this.roles = roles;
	}


}
