package com.example.security.jwt.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Configuration
public class JWTUtils {
    @Value("${app.secret}")
    private String secret;


    //1. Generate Token
    public String generateToken(String subject) {
        return Jwts.builder()
                .setSubject(subject)
                .setIssuer("Salil")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(30)))
                .signWith(SignatureAlgorithm.HS512, secret.getBytes())
                .compact();
    }

    //2.Read Claims
    public Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secret.getBytes())
                .parseClaimsJws(token)
                .getBody();
    }

    //3. Read Exp Date
    public Date getExpDate(String token) {
        return getClaims(token).getExpiration();
    }

    //4.Read username/subject
    public String getUsername(String token) {
        return getClaims(token).getSubject();
    }


    //5. Validate Expiry date
    public boolean isTokenExpired(String token)
    {
        Date expDate = getExpDate(token);
        return expDate.before(new Date(System.currentTimeMillis()));
    }


    //6. validate user name in token and database exp date

    public boolean validateToken(String token, String username)
    {
        String tokenUserName = getUsername(token);
        return(username.equals(tokenUserName))&& !isTokenExpired(token);
    }

}
