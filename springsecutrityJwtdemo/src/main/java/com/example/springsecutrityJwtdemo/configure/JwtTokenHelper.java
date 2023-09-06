package com.example.springsecutrityJwtdemo.configure;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.spec.InvalidKeySpecException;
import java.util.Collection;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;

@Component
	public class JwtTokenHelper{
	
	@Value("${jwt.secret.app}")
	private String appName;
	@Value("${jwt.auth.secret_key}")
	private String secretKey;
	@Value("${jwt.auth.expires_in}")
	private int expriesIn;
	private Claims getAllClaimsFromToken(String token){
	SecretKey key =Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
	Claims claims;
	try {
	claims=Jwts.parserBuilder()
	       .setSigningKey(key)
		.build()
		.parseClaimsJws(token)
		.getBody();
	} catch (Exception e) {
		// TODO: handle exceptioncla
		claims=null;
	}
	return claims;
	}

	public String getUsernameFromToken(String token){
	String username;
	try{
	final Claims claims = this.getAllClaimsFromToken(token);
	username=claims.getSubject();
	}catch (Exception e) {
		username=null;
	}
	return username;
	}

	public String generateToken (String username,Collection<? extends GrantedAuthority> authorities) throws InvalidKeySpecException,
			NoSuchFieldException{
	//Hash-based Message Authentication codes -Secure hash algorthim
	SecretKey key =Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
	return Jwts.builder()
	.setIssuer(appName)
	.setSubject(username)
	.setIssuedAt(new Date())
	.claim("role", authorities)
	.setExpiration(generateExpirationDate())//2nd part payload
	.signWith(key)//3rd pard of jwt tokens
	
	.compact();
	}
	private Date generateExpirationDate(){
    return new Date(new Date().getTime()+expriesIn*1000); 
	}
	public Boolean validationTokens(String token,UserDetails userDetails){
	final String username= getUsernameFromToken(token);
	return(
	username != null &&
	username.equals(userDetails.getUsername())&&
	!IsTokenExpired(token)
	);
	}
	 public boolean IsTokenExpired(String token)
	{
	Date expireDate=getExpirationDate(token);
	return expireDate.before(new Date());
	}
	
	 public Date getExpirationDate(String token){
	Date expireDate;
	try{
	final Claims claims= this.getAllClaimsFromToken(token);
	expireDate = claims.getExpiration();
	}catch (Exception e)
	{
	expireDate=null;
	}
	return expireDate;
	}
	public Date getIssuedAtDateFromToken(String token){
	Date issueAt;
	try{
	final Claims claims = this.getAllClaimsFromToken(token);
	issueAt=claims.getIssuedAt();
	} catch(Exception e){
	issueAt = null;
	}
	return issueAt;
	}
	public String getToken( HttpServletRequest request){
	String authHeader = this.getAuthHeaderFromHeader( request);
	if (authHeader != null && authHeader.startsWith("Bearer ")){
	 return authHeader.substring(7);
	}
	return null;
	}
//	
   private String getAuthHeaderFromHeader(HttpServletRequest request) {
	   return  request.getHeader("Authorization");
	}

}
