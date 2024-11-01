package com.example.GDSC_insight.config.jwt;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

    public String generateToken(String LoginId, String role) {

        return Jwts.builder().setSubject(LoginId).claim("roles", role).setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, JwtProperties.SECRET).compact();
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parser().setSigningKey(JwtProperties.SECRET).parseClaimsJws(token).getBody().getSubject();
    }

    public String getRoleFromToken(String token) {
        return Jwts.parser().setSigningKey(JwtProperties.SECRET).parseClaimsJws(token).getBody()
                .get("roles", String.class);
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(JwtProperties.SECRET).parseClaimsJws(token);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
