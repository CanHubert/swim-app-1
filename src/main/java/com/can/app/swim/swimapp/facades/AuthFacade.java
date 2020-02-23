package com.can.app.swim.swimapp.facades;

import com.can.app.swim.swimapp.auth.jwt.JWTUtils;
import com.can.app.swim.swimapp.entity.Role;
import com.can.app.swim.swimapp.entity.User;
import com.can.app.swim.swimapp.enums.EmailName;
import com.can.app.swim.swimapp.enums.EnumRole;
import com.can.app.swim.swimapp.helpers.email.EmailReceiver;
import com.can.app.swim.swimapp.helpers.email.IEmailSender;
import com.can.app.swim.swimapp.repository.RoleRepository;
import com.can.app.swim.swimapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.util.List;
import java.util.Optional;

@Component
public class AuthFacade {

    private AuthenticationManager authenticationManager;

    private UserRepository userRepository;

    private RoleRepository roleRepository;

    private PasswordEncoder encoder;

    private JWTUtils jwtUtils;

    private IEmailSender iEmailSender;

    public AuthFacade(AuthenticationManager authenticationManager,
                      UserRepository userRepository,
                      RoleRepository roleRepository,
                      PasswordEncoder encoder,
                      JWTUtils jwtUtils,
                      IEmailSender iEmailSender){
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
        this. iEmailSender = iEmailSender;
    }

    public Authentication authenticate(UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken){
        return this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);
    }

    public String generateJwtToken(Authentication authentication){
        return this.jwtUtils.generateJwtToken(authentication);
    }

    public List<Role> findRolesByNameIn(List<EnumRole> roleList){
        return  roleRepository.findByNameIn(roleList);
    }

    public Optional<Role> findRoleByName(EnumRole enumRole){
        return roleRepository.findByName(enumRole);
    }

    public boolean isUserExistsByUsername(String username){
        return userRepository.existsByUsername(username);
    }

    public boolean isUserExistsByEmail(String email){
        return userRepository.existsByEmail(email);
    }

    public void saveUser(User user){
        userRepository.save(user);
    }

    public PasswordEncoder getEncoder(){
        return encoder;
    }

    public void sendEmail(EmailName emailName, EmailReceiver receiver) throws MessagingException {
        iEmailSender.sendEmail(emailName, receiver);
    }
}
