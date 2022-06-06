package com.SEACORP.EastSea.Controllers;

import com.SEACORP.EastSea.Auth.ERole;
import com.SEACORP.EastSea.Auth.Role;
import com.SEACORP.EastSea.Auth.User;
import com.SEACORP.EastSea.Payload.Request.LoginRequest;
import com.SEACORP.EastSea.Payload.Request.SignupRequest;
import com.SEACORP.EastSea.Payload.Response.JwtResponse;
import com.SEACORP.EastSea.Payload.Response.MessageResponse;
import com.SEACORP.EastSea.Repositories.RoleRepository;
import com.SEACORP.EastSea.Repositories.UserRepository;
import com.SEACORP.EastSea.Security.Jwt.JwtUtils;
import com.SEACORP.EastSea.Security.Services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("api/auth")
public class AuthController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<JwtResponse> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(
                jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signupRequest) {
        if (userRepository.existsByUsername(signupRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email already in use. Please login or reset password"));
        }

        User user = new User(signupRequest.getUsername(), encoder.encode(signupRequest.getPassword()));
        Set<String> strRoles = signupRequest.getRoles();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER).orElseThrow(() -> new RuntimeException("Error: Role is not found"));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                    case "administrator":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);
                        break;

                    case "mod":
                    case "moderator":
                        Role modRule = roleRepository.findByName(ERole.ROLE_MODERATOR).orElseThrow(() -> new RuntimeException("Error: Role is not found"));
                        roles.add(modRule);
                        break;

                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER).orElseThrow(() -> new RuntimeException("Error: Role is not found"));
                        roles.add(userRole);
                        break;
                }
            });
        }
        user.setRoles(roles);
        userRepository.save(user);

        return new ResponseEntity(new MessageResponse("User registered successfully"), HttpStatus.CREATED);
    }
}
