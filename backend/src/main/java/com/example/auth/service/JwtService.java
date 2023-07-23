package com.example.auth.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
@Service
@Log4j2
public class JwtService {

    // Secret key for signing JWTs
    private static final String SECRET_KEY = "337336763979244226452948404D6351655468576D5A7134743777217A25432A";

    // Extract username from token
    public String extractUserEmail(String jwtToken) {

        log.info("Extracting username from token");

        return extractClaim(jwtToken, Claims::getSubject);
    }

    // Generate JWT from user details
    public String generateToken(UserDetails userDetails) {

        log.info("Generating JWT for user: {}", userDetails.getUsername());

        return generateToken(new HashMap<>(), userDetails);
    }

    // Generate JWT with additional claims
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {

        log.info("Generating JWT with extra claims for user: {}", userDetails.getUsername());

        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000)) // 1 day
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Validate JWT 
    public boolean validateToken(String jwtToken, UserDetails userDetails) {

        log.info("Validating token");

        String username = extractUserEmail(jwtToken);

        boolean isValid = (username.equals(userDetails.getUsername())) && !isTokenExpired(jwtToken);

        if(!isValid) {
            log.warn("Token validation failed");
        }

        return isValid;
    }

    // Check if token expired
    private boolean isTokenExpired(String jwtToken) {
        log.info("Checking token expiration");
        return extractExpiration(jwtToken).before(new Date());
    }

    // Extract expiration date
    private Date extractExpiration(String jwtToken) {
        return extractClaim(jwtToken, Claims::getExpiration);
    }

    // Extract claims
    public <T> T extractClaim(String jwtToken, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(jwtToken);
        return claimsResolver.apply(claims);
    }

    // Parse claims
    private Claims extractAllClaims(String jwtToken) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(jwtToken)
                .getBody();
    }

    // Get signing key
    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
