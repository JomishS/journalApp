package net.engineeringdigest.journalApp.utilis;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

    private String SECRET_KEY = "TaK+HaV^uvCHEFsEVfypW#7g9^k*Z8$V";

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public String extractUsername(String token){
        Claims claims=extractAllClaims(token);
        return claims.getSubject();
    }

    public Date extractExpiration(String token){
        return extractAllClaims(token).getExpiration();
    }

    public String extractClaim(String token,String claim){
        return extractAllClaims(token).get(claim,String.class);
    }

    private Claims extractAllClaims(String token){
        return Jwts.parser()
        .verifyWith(getSigningKey())
        .build()
        .parseSignedClaims(token) //parses the object,verifies its signature and returns a JWT<Claims> object
        .getPayload(); //gives you the claims the data you want
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .header().empty().add("typ", "JWT")     
                .and()
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 20))
                .signWith(getSigningKey())
                .compact();
    }

    public Boolean validateToken(String token){
        return  !isTokenExpired(token);
    }
}
