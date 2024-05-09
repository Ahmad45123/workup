package com.workup.users.commands.utils;

import io.jsonwebtoken.*;
import java.util.Date;

public class AuthTokenHandler {
  private static final String SECRET_KEY = System.getenv("SECRET_KEY");
  private static final JwtParser parser = Jwts.parserBuilder().setSigningKey(SECRET_KEY).build();

  public static String generateToken(String email) {
    Date now = new Date();
    Date expiryDate = new Date(now.getTime() + 3600000); // Token expires in 1 hour

    return Jwts.builder()
        .setSubject(email)
        .setIssuedAt(now)
        .setExpiration(expiryDate)
        .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
        .compact();
  }

  public static String decodeToken(String token) {
    Jws<Claims> claimsJws = parser.parseClaimsJws(token);
    Claims body = claimsJws.getBody();
    return body.getSubject();
  }
}
