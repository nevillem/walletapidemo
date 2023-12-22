package com.walletapidemo.walletapidemo.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtService {
//  @Value("${application.security.jwt.secret-key}")
 private String SECRETE_KEY= "1bf2a627f78a18a3ea876d35d6c2ed7fcc364efc85a42fe3c78b2a22ec7f67da";
//  @Value("${application.security.jwt.expiration}")
 private long jwtExpiration=86400000; //a day;
//  @Value("${application.security.jwt.refresh-token.expiration}")
 private long refreshExpiration=604800000; // 7 days;

 public String extractUsername( String token){
    return extractClaim(token, Claims::getSubject);

    }

    //create a generic method
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
      }

      public String generateToken(UserDetails userDetails) {
      return buildToken(new HashMap<>(), userDetails,jwtExpiration);
    }    

//generate token without using claims and userdetails
  public String buildToken(
          Map<String, Object> extraClaims,
          UserDetails userDetails,
          long expiration
  ) {
    return Jwts
            .builder()
            .setClaims(extraClaims)
            .setSubject(userDetails.getUsername())
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis()+expiration))
            .signWith(getSignInKey(), SignatureAlgorithm.HS256)
            .compact();
  }

  //method to valid token
  public boolean isTokenValid(String token, UserDetails userDetails) {
    final String username = extractUsername(token);
    return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
  }

  private boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }
    private Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
}

    private Claims extractAllClaims(String token){
        //to ensure the person sending this toekn is the same person it claims to be
        
    return Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token).getBody();
    }

    public String generateRefreshToken(
      UserDetails userDetails
  ) {
    return buildToken(new HashMap<>(), userDetails,refreshExpiration);
  }

    private Key getSignInKey() {
    byte[] keyBytes = Decoders.BASE64.decode(SECRETE_KEY);
    return Keys.hmacShaKeyFor(keyBytes);
  }

}
