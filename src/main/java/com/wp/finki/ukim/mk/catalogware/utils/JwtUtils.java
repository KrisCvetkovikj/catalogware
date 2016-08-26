package com.wp.finki.ukim.mk.catalogware.utils;

import com.wp.finki.ukim.mk.catalogware.model.security.AuthUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Borce on 26.08.2016.
 */
@Component
public class JwtUtils {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${jwt.token.secret}")
    private String secret;

    @Value("${jwt.token.expiration}")
    private long expiration;

    public String getUsernameFromToken(String token) {
        String username;
        try {
            final Claims claims = this.getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception exp) {
            logger.error("error occurred while reading username from token");
            exp.printStackTrace();
            username = null;
        }
        return username;
    }

    public Date getCratedAtDateFromToken(String token) {
        Date createdAt;
        try {
            final Claims claims = this.getClaimsFromToken(token);
            createdAt = new Date((Long) claims.get("created"));
        } catch (Exception exp) {
            logger.error("error occurred while reading token created at data");
            exp.printStackTrace();
            createdAt = null;
        }
        return createdAt;
    }

    public Date getExpirationDateFromToken(String token) {
        Date expiration;
        try {
            final Claims claims = this.getClaimsFromToken(token);
            expiration = claims.getExpiration();
        } catch (Exception exp) {
            logger.error("error occurred while reading token expiration date");
            exp.printStackTrace();
            expiration = null;
        }
        return expiration;
    }

    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception exp) {
            logger.error("error occurred while parsing the token");
            exp.printStackTrace();
            claims = null;
        }
        return claims;
    }

    public Date generateCurrentDate() {
        return new Date(System.currentTimeMillis());
    }

    public Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + this.expiration * 1000);
    }

    public boolean isTokenExpired(String token) {
        final Date expiration = this.getExpirationDateFromToken(token);
        return expiration.before(this.generateCurrentDate());
    }

    public String generateToken(AuthUser authUser) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(Claims.SUBJECT, authUser.getUsername());
        claims.put("created", this.generateCurrentDate());
        return this.generateToken(claims);
    }

    private String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(this.generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, this.secret)
                .compact();
    }

    public boolean canTokenBeRefreshed(String token) {
        final Date created = this.getCratedAtDateFromToken(token);
        return ! this.isTokenExpired(token);
    }

    public String refreshToken(String token) {
        String refreshedToken;
        try {
            final Claims claims = this.getClaimsFromToken(token);
            claims.put("created", this.generateCurrentDate());
            refreshedToken = this.generateToken(claims);
        } catch (Exception exp) {
            logger.error("error occurrd while refreshing the token");
            exp.printStackTrace();
            refreshedToken = null;
        }
        return refreshedToken;
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        AuthUser authUser = (AuthUser) userDetails;
        final String username = this.getUsernameFromToken(token);
        final Date created = this.getCratedAtDateFromToken(token);
        final Date expiration = this.getExpirationDateFromToken(token);
        return authUser.getUsername().equals(username) && !(this.isTokenExpired(token));
    }
}
