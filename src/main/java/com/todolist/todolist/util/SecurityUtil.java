package com.todolist.todolist.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.todolist.todolist.config.properties.SecurityConstants;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class SecurityUtil {

    private SecurityUtil() {
    }

    public static Map<String, String> getTokenMap(String accessToken, String refreshToken) {
        final Map<String, String> tokens = new HashMap<>();
        tokens.put("access_token", accessToken);
        tokens.put("refresh_token", refreshToken);

        return tokens;
    }

    public static String getAccessToken(UserDetails user, String requestUrl, Long userId) {
        return JWT.create()
                .withSubject(String.valueOf(userId))
                .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstants.ACCESS_TOKEN_EXPIRE_TIME))
                .withIssuer(requestUrl)
                .withClaim("roles", getRolesFromUser(user))
                .sign(SecurityConstants.ALGORITHM);
    }

    public static String getRefreshToken(UserDetails user, String requestUrl) {
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstants.REFRESH_TOKEN_EXPIRE_TIME))
                .withIssuer(requestUrl)
                .sign(SecurityConstants.ALGORITHM);
    }

    public static DecodedJWT getDecodedJwtFromBearerToken(String bearerToken) {
        String token = bearerToken.substring(SecurityConstants.BEARER_PREFIX_LEN);
        Algorithm algorithm = Algorithm.HMAC256(SecurityConstants.BASE64_SECRET_KEY.getBytes());
        JWTVerifier verifier = JWT.require(algorithm).build();
        return verifier.verify(token);
    }

    public static String getSubjectFromBearerToken(String bearerToken) {
        DecodedJWT decodedJWT = getDecodedJwtFromBearerToken(bearerToken);
        return decodedJWT.getSubject();
    }

    private static List<String> getRolesFromUser(UserDetails user) {
        return user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.toList());
    }

}
