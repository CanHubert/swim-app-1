package com.can.app.swim.swimapp.auth.jwt;

import com.can.app.swim.swimapp.auth.services.UserDetailsImpl;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.logging.Logger;


@Component
public class JWTUtils {
	private Logger logger = Logger.getLogger(getClass().getName());

	@Value("${bezkoder.app.jwtSecret}")
	private String jwtSecret;

	@Value("${bezkoder.app.jwtExpirationMs}")
	private int jwtExpirationMs;

	public String generateJwtToken(Authentication authentication) {

		UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

		return Jwts.builder()
				.setSubject((userPrincipal.getUsername()))
				.setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
				.signWith(SignatureAlgorithm.HS512, jwtSecret)
				.compact();
	}

	public String getUserNameFromJwtToken(String token) {
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
	}

	public boolean validateJwtToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
			return true;
		} catch (MalformedJwtException e) {
			logger.warning("Invalid JWT token: {}"+ e.getMessage());
		} catch (ExpiredJwtException e) {
			logger.warning("JWT token is expired: {}"+ e.getMessage());
		} catch (UnsupportedJwtException e) {
			logger.warning("JWT token is unsupported: {}"+ e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.warning("JWT claims string is empty: {}"+ e.getMessage());
		}

		return false;
	}
}
