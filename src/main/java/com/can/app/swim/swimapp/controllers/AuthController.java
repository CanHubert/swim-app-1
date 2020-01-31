package com.can.app.swim.swimapp.controllers;

import com.can.app.swim.swimapp.auth.jwt.JWTUtils;
import com.can.app.swim.swimapp.auth.payloads.requests.LoginRequest;
import com.can.app.swim.swimapp.auth.payloads.requests.SignupRequest;
import com.can.app.swim.swimapp.auth.payloads.responses.JwtResponse;
import com.can.app.swim.swimapp.auth.payloads.responses.MessageResponse;
import com.can.app.swim.swimapp.auth.services.UserDetailsImpl;
import com.can.app.swim.swimapp.entity.Role;
import com.can.app.swim.swimapp.entity.User;
import com.can.app.swim.swimapp.enums.EnumRole;
import com.can.app.swim.swimapp.repository.RoleRepository;
import com.can.app.swim.swimapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private JWTUtils jwtUtils;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok(new JwtResponse(jwt,
												 userDetails.getId(), 
												 userDetails.getUsername(), 
												 userDetails.getEmail(), 
												 roles));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Username is already taken!"));
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Email is already in use!"));
		}

		// Create new user's account
		User user = new User(signUpRequest.getUsername(),
							 signUpRequest.getFirstName(),
							 signUpRequest.getLastName(),
							 signUpRequest.getEmail(),
							 encoder.encode(signUpRequest.getPassword()));

		Set<String> strRoles = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();

		if (strRoles == null)
		{
			Role userRole = roleRepository.findByName(EnumRole.ROLE_USER);
			throwExceptionIfRoleIsNull(userRole);
					
			roles.add(userRole);
		}
		else 
		{
			strRoles.forEach(role -> 
			{
				switch (role) {
				case "admin":
					Role adminRole = roleRepository.findByName(EnumRole.ROLE_ADMIN);
					throwExceptionIfRoleIsNull(adminRole);
					roles.add(adminRole);
					break;
				case "instructor":
					Role instructorRole = roleRepository.findByName(EnumRole.ROLE_INSTRUCTOR);
					throwExceptionIfRoleIsNull(instructorRole);
					roles.add(instructorRole);
					break;
				default:
					Role userRole = roleRepository.findByName(EnumRole.ROLE_USER);
					throwExceptionIfRoleIsNull(userRole);
					roles.add(userRole);
				}
			});
		}

		user.setRoles(roles);
		userRepository.save(user);

		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}
	
	private void throwExceptionIfRoleIsNull(Role role) {
		if(role == null)
		{
			throw new RuntimeException("Error: Role is not found.");
		}
	}
}
