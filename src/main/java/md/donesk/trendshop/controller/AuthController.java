package md.donesk.trendshop.controller;

import jakarta.validation.Valid;
import md.donesk.trendshop.dto.request.LoginDTO;
import md.donesk.trendshop.dto.request.RegisterRequest;
import md.donesk.trendshop.dto.response.JwtResponse;
import md.donesk.trendshop.jwt.JwtTokenProvider;
import md.donesk.trendshop.jwt.UserDetailsImpl;
import md.donesk.trendshop.model.Customer;
import md.donesk.trendshop.model.Role;
import md.donesk.trendshop.repository.CustomerRepository;
import md.donesk.trendshop.repository.RoleRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/trend/auth")
@Validated
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final CustomerRepository customerRepository;
    private final RoleRepository roleRepo;
    private final PasswordEncoder encoder;
    private final JwtTokenProvider jwtUtils;

    public AuthController(AuthenticationManager authenticationManager, CustomerRepository customerRepository, RoleRepository roleRepo, PasswordEncoder encoder, JwtTokenProvider jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.customerRepository = customerRepository;
        this.roleRepo = roleRepo;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
    }


    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(
            @Valid @RequestBody LoginDTO loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<? extends GrantedAuthority> roles = userDetails.getAuthorities().stream().collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(
            @Valid @RequestBody RegisterRequest signUpRequest) {

        if (customerRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body("Email is already in use!");
        }

        Customer customer = new Customer(signUpRequest.getFirstName(),
                signUpRequest.getLastName(),
                signUpRequest.getEmail(),
                signUpRequest.getPhone(),
                encoder.encode(signUpRequest.getPassword()));

        Set<Role> roles = new HashSet<>();
        int customerRoleId = 1;
        Optional<Role> customerRole = roleRepo.findById(customerRoleId);

        if(customerRole.isPresent()) {
            roles.add(customerRole.get());

            customer.setRoles(roles);
            customerRepository.save(customer);
        }else {
            throw new RuntimeException("Role not found: Customer");
        }
        return ResponseEntity.ok("User registered successfully!");
    }
}
