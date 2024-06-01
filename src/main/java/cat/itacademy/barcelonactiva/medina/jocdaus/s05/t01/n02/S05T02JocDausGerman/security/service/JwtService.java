package cat.itacademy.barcelonactiva.medina.jocdaus.s05.t01.n02.S05T02JocDausGerman.security.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.hibernate.annotations.DialectOverride;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    private static final String SECRET_KEY_PROPERTY = "your-256-bit-secret";
    private Key secretKey;

    @PostConstruct
    public void init() {
        if (SECRET_KEY_PROPERTY == null || SECRET_KEY_PROPERTY.isEmpty() || SECRET_KEY_PROPERTY.equals("your-256-bit-secret")) {
            // Generate a new key if SECRET_KEY_PROPERTY is not provided or is the default placeholder
            secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
            String base64Key = Base64.getEncoder().encodeToString(secretKey.getEncoded());
            System.out.println("Generated key: " + base64Key);
            // Log the generated key for development purposes, but remember to securely store it in a real application
        } else {
            // Use the provided secret key
            byte[] keyBytes = Base64.getDecoder().decode(SECRET_KEY_PROPERTY);
            secretKey = Keys.hmacShaKeyFor(keyBytes);
        }
    }

    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public String getUserName(String token) {
        return getClaim(token, Claims::getSubject);
    }

    public <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String userName = getUserName(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return getExpiration(token).before(new Date());
    }

    private Date getExpiration(String token) {
        return getClaim(token, Claims::getExpiration);
    }
}
