package tj.alimov.userservice.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Service
public class JwtService {


    private final Key SECRET_KEY; //Keys.hmacShaKeyFor(System.getenv("jwt.secret_key").getBytes(StandardCharsets.UTF_8));

    public JwtService(@Value("${jwt.secret_key}") String key){
        System.out.print(String.format(" KEY = %s",key));
        SECRET_KEY = Keys.hmacShaKeyFor(Decoders.BASE64.decode(key));
    }


    public String generateToken(String username,int duration){
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000*60*60*24*31 *duration))
                .signWith(SECRET_KEY)
                .compact();
    }
    public boolean validateToken(String token,String username){
        return username.equals(extractUsername(token)) && !isTokenExpired(token);
    }
    public String extractUsername(String token){
        return extractClaims(token).getSubject();
    }

    public boolean isTokenExpired(String token){
        return extractClaims(token).getExpiration().before(new Date());
    }

    public Claims extractClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();

    }



}
