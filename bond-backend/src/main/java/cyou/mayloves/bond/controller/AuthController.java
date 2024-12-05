package cyou.mayloves.bond.controller;

import cyou.mayloves.bond.dto.LoginRequest;
import cyou.mayloves.bond.dto.RegisterRequest;
import cyou.mayloves.bond.entity.User;
import cyou.mayloves.bond.service.UserService;
import cyou.mayloves.bond.utils.JwtUtils;
import cyou.mayloves.bond.utils.Result;
import cyou.mayloves.bond.utils.UserIdContextHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    /**
     * 用户注册
     *
     * @param registerRequest 注册请求
     * @return 注册结果
     */
    @PostMapping("/register")
    public Result<String> register(@RequestBody RegisterRequest registerRequest) {
        boolean success = userService.registerUser(registerRequest);
        if (success) {
            return Result.success();
        } else {
            return Result.error("Username already exists");
        }
    }

    /**
     * 用户登录
     *
     * @param loginRequest 登录请求
     * @return 登录结果
     */
    @PostMapping("/login")
    public Result<Map<String, Object>> login(@RequestBody LoginRequest loginRequest) {
        User user = userService.authenticateUser(loginRequest);
        if (user != null) {
            String token = JwtUtils.generateToken(user.getId(), user.getUsername());
            Map<String, Object> data = new HashMap<>();
            data.put("token", token);
            return Result.success(data);
        } else {
            return Result.error("Invalid username or password");
        }
    }

    @GetMapping("/info")
    public Result<User> login() {
        return Result.success(userService.getById(UserIdContextHolder.getUserId()));
    }

    /**
     * 验证 Token 是否有效
     *
     * @param token JWT 令牌
     * @return 验证结果
     */
    @GetMapping("/validate")
    public Result<Map<String, Object>> validateToken(@RequestParam String token) {
        String username = JwtUtils.validateToken(token);
        Map<String, Object> data = new HashMap<>();
        if (username != null) {
            data.put("valid", true);
            data.put("username", username);
            return Result.success(data);
        } else {
            data.put("valid", false);
            return Result.error("Token is invalid or expired");
        }
    }
}