package com.example.jwttokenreview.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtTokenUtil {
    public static String createToken(String username, String key, long expireTimeMs) {
        Claims claims = Jwts.claims(); // 일종의 map
        claims.put("username", username);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expireTimeMs))
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }

    // Token 만료 Check
    public static boolean isExpired(String token, String key) {
        Date expiredDate = extractClaims(token, key).getExpiration();

        // Token 만료 기간이 지금보다 이전이면 true return
        if(expiredDate.before(new Date())) {
            return true;
        }
        return false;
    }

    public static String getUsername(String token, String key) {
        return extractClaims(token, key).get("username").toString();
    }

    private static Claims extractClaims(String token, String key) {
        return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
    }
}
