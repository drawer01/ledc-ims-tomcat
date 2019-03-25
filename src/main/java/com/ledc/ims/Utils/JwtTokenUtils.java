package com.ledc.ims.Utils;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

/**
 *
 * <p> Token 生成工具</p>
 */
public class JwtTokenUtils {

    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    private static final String SECRET = "jwt_secret_ims";
    private static final String ISS = "drawer";
    //private static final String ROLE_CLAIMS = "role";
    // 过期时间是3600秒，既是1个小时
    //private static final long EXPIRATION = 3600L;
    // 选择了记住我之后的过期时间为7天
    //private static final long EXPIRATION_REMEMBER = 604800L;
    // 创建token
    public static String createToken(String username, Integer expiration) {

        //HashMap<String,Object> map = new HashMap<>();
        //map.put(ROLE_CLAIMS, role);
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, SECRET)
               //.setClaims(map)
                .setIssuer(ISS)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
                .compact();

    }

    // 从token中获取用户名
    public static String getUsername(String token){
        return getTokenBody(token).getSubject();
    }

    // 是否已过期
    public static boolean isExpiration(String token){
        return getTokenBody(token).getExpiration().before(new Date());
    }

    public static Claims getTokenBody(String token){
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody();
    }

}
