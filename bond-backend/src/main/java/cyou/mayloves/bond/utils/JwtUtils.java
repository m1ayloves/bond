package cyou.mayloves.bond.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class JwtUtils {

    private static final String SECRET_KEY = "ThisKeyIsExactly32CharactersLong";

    private static final long EXPIRATION_TIME = TimeUnit.MINUTES.toMillis(30); // 30 mins in milliseconds

    private static final Key KEY = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    /**
     * 生成 JWT Token
     *
     * @param username 用户名
     * @return 生成的令牌
     */
    public static String generateToken(Integer userId, String username) {
        return Jwts.builder()
                .setSubject(username)
                .claim("user_id", userId) // 添加 user_id 到 Token
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * 验证 JWT Token 并返回用户名
     *
     * @param token JWT 令牌
     * @return 用户名
     */
    public static String validateToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(KEY)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getSubject();
        } catch (JwtException | IllegalArgumentException e) {
            return null; // Token无效或过期
        }
    }

    /**
     * 检查 Token 是否过期
     *
     * @param token JWT 令牌
     * @return 是否过期
     */
    public static boolean isTokenExpired(String token) {
        try {
            Date expiration = Jwts.parserBuilder()
                    .setSigningKey(KEY)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getExpiration();
            return expiration.before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            return true;
        }
    }

    public static Integer getUserIdFromToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(KEY)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claims.get("user_id", Integer.class);
        } catch (JwtException | IllegalArgumentException e) {
            return null;
        }
    }
}