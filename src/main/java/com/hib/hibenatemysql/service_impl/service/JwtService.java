package com.hib.hibenatemysql.service_impl.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private static final int token_expiry_time = 1000 * 120 * 24;
    private static final String SECRET_KEY = "1cc8f4ffc76f59f1499c34509854bf73cc612dbe375bf9aa806ffe8b2c333ab4";

    public String generateToken(String userId) {
        Map<String, Object> claims = new HashMap<>();
        return tokenLogic(claims, userId);
    }

    private String tokenLogic(Map<String, Object> claims, String userId) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userId)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + token_expiry_time))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUserId(String token) {
        return extractClaim(token, Claims::getSubject);
    }


    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
        final Claims claims = extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiry(token).before(new Date());
    }

    private Date extractExpiry(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String userId = extractUserId(token);
        return (userDetails.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private Key getSignInKey() {
        byte[] key = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(key);
    }

}
