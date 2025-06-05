package com.example.expense.expense_tracker.util;



import java.util.Base64;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;

import io.jsonwebtoken.JwtException;

import io.jsonwebtoken.Jwts;

import io.jsonwebtoken.SignatureAlgorithm;

import io.jsonwebtoken.security.Keys;
@Component
public class JwtUtil {
    private final SecretKey secretKey;
    private static final long EXPIRATION_TIME =60000;
    @Autowired
    public JwtUtil(SecretKey secretKey) {
        this.secretKey = secretKey;
    }
    public String generateToken(String username, int userId) {
        System.out.println("Generating Token for User: " + username + ", UserID: " + userId);
        String base64Key = Base64.getEncoder().encodeToString(secretKey.getEncoded());
        System.out.println("Secret Key (Base64): " + base64Key);
        return Jwts.builder()
                .setSubject(username)
                .claim("userid", userId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(secretKey)
                .compact();
    }
    public Claims validateToken(String token) throws Exception {
        try {
            String secretKeyBase64 = Base64.getEncoder().encodeToString(secretKey.getEncoded());
            System.out.println("Validating Token: " + token);
            System.out.println("Secret Key (Base64): " + secretKeyBase64);
            return Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            System.err.println("Token validation failed: " + e.getMessage());
            throw new Exception("Invalid token.", e);
        }
    }
    
    public String getUsernameFromToken(String token) throws Exception {
        return validateToken(token).getSubject(); 
    }
  
    public int getUserIdFromToken(String token) throws Exception {
        Object userId = validateToken(token).get("userid"); 
        if (userId instanceof Integer) {
            return (int) userId;
        } else {
            throw new Exception("Invalid user ID in token.");
        }
    }
    public boolean isTokenExpired(String token) throws Exception {
        Claims claims = validateToken(token); 
        Date expirationDate = claims.getExpiration();
        return expirationDate.before(new Date());
    }
    public boolean TokenExpired(String token) {
    	        Date expirationDate = getExpirationDate(token);
    	        if (expirationDate == null) {
    	            return true;
    	        }
    	        Date currentTime = new Date();
    	        return expirationDate.before(currentTime);
    	    }
    	    private Date getExpirationDate(String token) {
    	        try {
    	            Claims claims = Jwts.parserBuilder()
    	                    .setSigningKey(secretKey)
    	                    .build()
    	                    .parseClaimsJws(token)
    	                    .getBody();
    	            return claims.getExpiration();
    	        } catch (JwtException e) {
    	            return null;
    	        }



}}
//import java.nio.charset.StandardCharsets;
//import java.util.Base64;
//import java.util.Date;
//
//import javax.crypto.SecretKey;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Service;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import io.jsonwebtoken.security.Keys;
//
//@Service
//public class JwtUtil {
//	
//
//    private final SecretKey secretKey;
//
//    @Autowired
//    public JwtUtil(SecretKey secretKey) {
//        this.secretKey = secretKey;
//    }
//    
//    private Logger logger=LoggerFactory.getLogger(JwtUtil.class);
//    private static final long expirationTime = 60000; 
//    
//    private static final String SECRET_KEY_BASE64 = "dc79a220099deb3cb52b7b649578b78d90aacaf2f18b904b781961c825da61516abd8408af17964bba564b2eadc66a532f861fe6952ba6b6ef3e3df7408c76a2f6d22498f0db01be9e7827e787c6af71d46350c54de83d7b639148646a30ecbeefac85dfa190b82c2b4593c85958041c2133812038eb84c468ff65b07cfcae63592d2410a1c80a15bc8c173c8dffdc091984d569b4e394a6aeb757793b8b7b67e12194c6224b0fecac6bfd6571f2df4a1b44654b469191f5bbe26c96ba6cc73126632b2049739cc7f2d640195d24e3bbc765ece8fec896509dc36d4cf2531690ff7cbd099abe245f1bd51b98cb4ed4a7209d480092544bfe24a885ec30a99071";
//
//   
//    public SecretKey secretKey() {
//        byte[] decodedKey = Base64.getDecoder().decode(SECRET_KEY_BASE64);
//        return Keys.hmacShaKeyFor(decodedKey);
//    }
//
//  
//    public String generateToken(String username, int userId) {
//        return Jwts.builder()
//                .setSubject(username)
//                .claim("userid", userId)
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
//                .signWith(secretKey(),SignatureAlgorithm.HS256)
//                .compact();
//    }
//
//   
//    public Claims validateToken(String token) throws Exception {
//    	try {
//    		   
//    	    return Jwts.parserBuilder()
//    	            .setSigningKey(secretKey())
//    	            .build()
//    	            .parseClaimsJws(token)
//    	            .getBody();
//    	} catch (io.jsonwebtoken.ExpiredJwtException e) {
//    	    throw new Exception("Token is expired", e);
//    	} catch (io.jsonwebtoken.SignatureException e) {
//    	    throw new Exception("Invalid token signature", e);
//    	} catch (Exception e) {
//    	    throw new Exception("Invalid or expired token", e);
//    	}
//
//    }
//
//    
//    public String getUsernameFromToken(String token) throws Exception {
//        return validateToken(token).getSubject();
//    }
//
//   
//    public int getUserIdFromToken(String token) throws Exception {
//        return (Integer) validateToken(token).get("userid");
//    }
////
//    
//    public boolean isTokenExpired(String token) throws Exception {
//        Date expirationDate = validateToken(token).getExpiration();
//        return expirationDate.before(new Date());
//    }
//}

//package com.example.expense.expense_tracker.util;
////package expense_tracker.utility;
//
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.JwtException;
//import io.jsonwebtoken.security.Keys;
//import org.springframework.stereotype.Component;
//
//import java.security.Key;
//import java.util.Date;
//
//@Component
//public class JwtUtil {
//
//    private final Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
//
//    public String generateToken(String username, int userId) {
//        long now = System.currentTimeMillis();
//        Date expiryDate = new Date(now + 1000 * 60 * 10);
//
//        return Jwts.builder()
//                .setSubject(username)
//                .claim("userId", userId)
//                .setIssuedAt(new Date(now))
//                .setExpiration(expiryDate)
//                .signWith(SignatureAlgorithm.HS256, secretKey)
//                .compact();
//    }
//
//    public String getUsernameFromToken(String token) {
//        try {
//            Claims claims = Jwts.parserBuilder()
//                    .setSigningKey(secretKey)
//                    .build()
//                    .parseClaimsJws(token)
//                    .getBody();
//            return claims.getSubject();
//        } catch (JwtException e) {
//
//            return null;
//        }
//    }
//
//    public int getUserIdFromToken(String token) {
//        try {
//            Claims claims = Jwts.parserBuilder()
//                    .setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
//            return claims.get("userId", Integer.class);
//        }
//        catch (JwtException e){
//            return -1;
//        }
//    }
//
//    public boolean validateToken(String token, String username) {
//        String tokenUsername = getUsernameFromToken(token);
//        return (tokenUsername != null && tokenUsername.equals(username) && !isTokenExpired(token));
//    }
//
//
//    public boolean isTokenExpired(String token) {
//        Date expirationDate = getExpirationDate(token);
//        if (expirationDate == null) {
//            return true;
//        }
//        Date currentTime = new Date();
//        return expirationDate.before(currentTime);
//    }
//
//    private Date getExpirationDate(String token) {
//        try {
//            Claims claims = Jwts.parserBuilder()
//                    .setSigningKey(secretKey)
//                    .build()
//                    .parseClaimsJws(token)
//                    .getBody();
//            return claims.getExpiration();
//        } catch (JwtException e) {
//
//            return null;
//        }
//    }
//
//
//    public boolean isTokenValid(String token) {
//        try {
//            return !isTokenExpired(token);
//        } catch (JwtException e) {
//            return false;
//        }
//    }
//}
