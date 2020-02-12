package com.can.app.swim.swimapp.controllers;

import com.can.app.swim.swimapp.auth.jwt.JWTUtils;
import com.can.app.swim.swimapp.auth.payloads.requests.LoginRequest;
import com.can.app.swim.swimapp.auth.payloads.requests.SignupRequest;
import com.can.app.swim.swimapp.auth.payloads.responses.JwtResponse;
import com.can.app.swim.swimapp.auth.payloads.responses.MessageResponse;
import com.can.app.swim.swimapp.auth.services.UserDetailsImpl;
import com.can.app.swim.swimapp.entity.EmailTemplate;
import com.can.app.swim.swimapp.entity.Role;
import com.can.app.swim.swimapp.entity.User;
import com.can.app.swim.swimapp.enums.EnumRole;
import com.can.app.swim.swimapp.helpers.MailValues;
import com.can.app.swim.swimapp.repository.EmailTemplateRepository;
import com.can.app.swim.swimapp.repository.RoleRepository;
import com.can.app.swim.swimapp.repository.UserRepository;
import com.can.app.swim.swimapp.services.EmailService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
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
	private EmailTemplateRepository emailTemplateRepository;

	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private JWTUtils jwtUtils;

	@Autowired
	private EmailService emailService;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);


		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.toList());

		return ResponseEntity.ok(new JwtResponse(jwt, userDetails, roles));
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
		User user = new User(signUpRequest,encoder);

		Set<String> strRoles = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();

		if (CollectionUtils.isEmpty(strRoles))
		{
			Role userRole = roleRepository.findByName(EnumRole.USER);
			throwExceptionIfRoleIsNull(userRole);
			roles.add(userRole);
		}
		else 
		{
			strRoles.forEach(roleName ->
			{
				Role role = roleRepository.findByName(EnumRole.getByName(roleName));
				throwExceptionIfRoleIsNull(role);
				roles.add(role);
			});
		}

		user.setRoles(roles);

		String message;
		try
		{
			sendWelcomeEmail(user);
			userRepository.save(user);
			message = "User registered successfully!";
		}
		catch (Exception ignored)
		{
			message = "User can't be registered!";
		}

		return ResponseEntity.ok(new MessageResponse(message));
	}

	private void sendWelcomeEmail(User user) throws MessagingException {
		Optional<EmailTemplate> emailTemplate = emailTemplateRepository.findByName("Register");
		if(emailTemplate.isPresent())
		{
			MailValues mailValues = new MailValues();
			mailValues.add("user", user.getFullName());
			emailService.sendHtmlVelocityMail(user.getEmail(), emailTemplate.get(), mailValues);
		}
	}

	private void throwExceptionIfRoleIsNull(Role role) {
		if(role == null)
		{
			throw new RuntimeException("Error: Role is not found.");
		}
	}

}
