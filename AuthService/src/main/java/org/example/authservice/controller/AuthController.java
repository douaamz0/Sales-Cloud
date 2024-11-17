package org.example.authservice.controller;

import java.util.Map;

import org.example.authservice.entities.User;
import org.example.authservice.repository.UserRepository;
import org.example.authservice.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired; // Allows for dependency injection
import org.springframework.http.HttpStatus; // Represents HTTP status codes
import org.springframework.http.ResponseEntity; // Used to return HTTP responses
import org.springframework.security.crypto.password.PasswordEncoder; // Interface for password encoding
import org.springframework.web.bind.annotation.*; // Contains annotations for RESTful web services


@CrossOrigin(origins = "http://localhost:4200") // Allows requests from specified origin
@RestController // Indicates that this class is a REST controller
@RequestMapping("/auth") // Maps all requests under /auth to this controller
public class AuthController {

    @Autowired
    private UserRepository userRepository; // Injected repository for accessing user data

    @Autowired
    private JwtUtil jwtUtil; // Injected JWT utility for token generation and validation

    private final PasswordEncoder passwordEncoder; // Inject PasswordEncoder for hashing passwords

    // Constructor to initialize userRepository and passwordEncoder
    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository; // Assign UserRepository
        this.passwordEncoder = passwordEncoder; // Assign PasswordEncoder
    }

    // Handles user registration
    @PostMapping("/register") // Maps POST requests to /auth/register
    public ResponseEntity<Map<String, String>> register(@RequestBody User user) {
        // Check if the username already exists in the database
        if (userRepository.findByUsername(user.getUsername()) != null) {
            // Return a bad request response if username exists
            return ResponseEntity.badRequest().body(Map.of("message", "Username already exists!"));
        }

        // Hash the password before saving to the database
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Save the new user to the database
        userRepository.save(user);
        // Return a response indicating successful registration
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "User registered successfully!"));
    }

    // Handles user login
    @PostMapping("/login") // Maps POST requests to /auth/login
    public ResponseEntity login(@RequestBody User user) {
        // Retrieve the user from the database using the provided username
        User foundUser = userRepository.findByUsername(user.getUsername());

        // Check if the user was found
        if (foundUser == null) {
            System.out.println("User not found: " + user.getUsername()); // Log for debugging
            // Return a response indicating the user was not found
            return new ResponseEntity<>("User not found", HttpStatus.UNAUTHORIZED);
        }

        // Check if the provided password matches the stored hashed password
        boolean passwordMatches = passwordEncoder.matches(user.getPassword(), foundUser.getPassword());
        System.out.println("Password matches: " + passwordMatches); // Log for debugging

        // If passwords match, generate a JWT token
        if (passwordMatches) {
            String token = jwtUtil.generateToken(foundUser.getUsername()); // Generate JWT token
            return new ResponseEntity<>(token, HttpStatus.OK); // Return the token
        } else {
            // If passwords don't match, return "Invalid credentials"
            return new ResponseEntity<>("Invalid credentials test", HttpStatus.UNAUTHORIZED);
        }
    }

    // Handles user logout
    @PostMapping("/logout") // Maps POST requests to /auth/logout
    public ResponseEntity logout() {
        // In stateless JWT authentication, logout can be handled by deleting the token client-side
        return new ResponseEntity<>("User logged out successfully!", HttpStatus.OK); // Confirm logout
    }

    // A simple hello endpoint to confirm authentication
    @GetMapping("/hello") // Maps GET requests to /auth/hello
    public ResponseEntity hello() {
        // Return a response indicating successful authentication
        return new ResponseEntity<>("Hello! You are authenticated.", HttpStatus.OK);
    }
}

