package org.example.authservice.utils;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

// The @Component annotation indicates that this class is a Spring-managed bean.
@Component
public class JwtUtil {

    // Generate a secure key for signing the JWT using the HS256 algorithm.
    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256); // Secure key for signing JWTs

    // Define the expiration time for the token (1 hour in milliseconds).
    private final long EXPIRATION_TIME = 1000 * 60 * 60; // 1 hour

    // Generate a token for the given username.
    public String generateToken(String username) {
        // Create an empty claims map for additional information if needed.
        Map claims = new HashMap<>();
        // Call the createToken method to create a JWT with the provided claims and username.
        return createToken(claims, username);
    }

    // Create a JWT using claims and a subject (username).
    private String createToken(Map claims, String subject) {
        // Build the JWT using the Jwts builder with claims, subject, issued date, expiration date, and signing key.
        return Jwts.builder()
                .setClaims(claims) // Set additional claims (if any)
                .setSubject(subject) // Set the subject (username)
                .setIssuedAt(new Date(System.currentTimeMillis())) // Set the token issue date
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // Set expiration date
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY) // Sign the token with the secret key
                .compact(); // Compact the JWT into a string
    }

    // Extract the username from the provided token.
    public String extractUsername(String token) {
        // Call the method to extract all claims from the token and get the subject.
        return extractAllClaims(token).getSubject();
    }

    // Extract all claims from the provided token.
    private Claims extractAllClaims(String token) {
        // Parse the token using the signing key and return the claims body.
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    // Validate the token by checking the extracted username and whether the token is expired.
    public boolean validateToken(String token, String username) {
        // Extract the username from the token.
        final String extractedUsername = extractUsername(token);
        // Check if the extracted username matches the provided username and if the token is not expired.
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }

    // Check if the token is expired.
    private boolean isTokenExpired(String token) {
        // Get the expiration date from the token claims and compare it with the current date.
        return extractAllClaims(token).getExpiration().before(new Date());
    }
}

