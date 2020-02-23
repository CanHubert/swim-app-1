package com.can.app.swim.swimapp.controllers;

import com.can.app.swim.swimapp.auth.payloads.requests.LoginRequest;
import com.can.app.swim.swimapp.auth.payloads.requests.SignupRequest;
import com.can.app.swim.swimapp.auth.payloads.responses.JwtResponse;
import com.can.app.swim.swimapp.auth.payloads.responses.MessageResponse;
import com.can.app.swim.swimapp.auth.services.UserDetailsImpl;
import com.can.app.swim.swimapp.entity.Role;
import com.can.app.swim.swimapp.entity.User;
import com.can.app.swim.swimapp.enums.EmailName;
import com.can.app.swim.swimapp.enums.EnumRole;
import com.can.app.swim.swimapp.facades.AuthFacade;
import com.can.app.swim.swimapp.helpers.ExceptionsUtil;
import com.can.app.swim.swimapp.helpers.email.EmailReceiver;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

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

	private AuthFacade authFacade;

	public AuthController(AuthFacade authFacade){
		this.authFacade = authFacade;
	}

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		Authentication authentication =
				authFacade.authenticate(
						new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = authFacade.generateJwtToken(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.toList());

		return ResponseEntity.ok(new JwtResponse(jwt, userDetails, authFacade.findRolesByNameIn(EnumRole.getByNames(roles))));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		if (authFacade.isUserExistsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Username is already taken!"));
		}

		if (authFacade.isUserExistsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Email is already in use!"));
		}

		// Create new user's account
		User user = new User(signUpRequest,authFacade.getEncoder());

		Set<String> strRoles = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();

		if (CollectionUtils.isEmpty(strRoles))
		{
			Optional<Role> $role = authFacade.findRoleByName(EnumRole.USER);
			Role role = getRole($role);
			roles.add(role);
		}
		else 
		{
			strRoles.forEach(roleName ->
			{
				Optional<Role> $role = authFacade.findRoleByName(EnumRole.getByName(roleName));
				Role role = getRole($role);
				roles.add(role);
			});
		}

		user.setRoles(roles);

		String message;
		try
		{
			authFacade.sendEmail(EmailName.WELCOME, new EmailReceiver(user));

			authFacade.saveUser(user);
			message = "User registered successfully!";
		}
		catch (Exception ignored)
		{
			message = "User can't be registered!";
		}

		return ResponseEntity.ok(new MessageResponse(message));
	}

	private Role getRole(Optional<Role> $role) {
		return $role.orElseThrow(ExceptionsUtil.throwRuntimeException("Error: Role is not found."));
	}
}
