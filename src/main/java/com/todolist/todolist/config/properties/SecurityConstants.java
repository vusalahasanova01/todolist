package com.todolist.todolist.config.properties;

import com.auth0.jwt.algorithms.Algorithm;

public class SecurityConstants {

    public static final String BASE64_SECRET_KEY = "CkVgX9jsM0t8SHaDUYcTDPr4Po2YYdPSy5YD5ZUssLlazeWaEOMK_BG21TYm5HldehvKP9001Mp6mMDbEY5DuA";
    public static final Algorithm ALGORITHM = Algorithm.HMAC256(SecurityConstants.BASE64_SECRET_KEY.getBytes());
    public static final int ACCESS_TOKEN_EXPIRE_TIME = 10 * 60 * 1000;
    public static final int REFRESH_TOKEN_EXPIRE_TIME = 30 * 60 * 1000;
    public static final String BEARER_PREFIX = "Bearer ";
    public static final int BEARER_PREFIX_LEN = 7;

    private SecurityConstants() {
    }

}
