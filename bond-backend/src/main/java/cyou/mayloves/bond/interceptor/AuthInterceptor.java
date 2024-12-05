package cyou.mayloves.bond.interceptor;

import cyou.mayloves.bond.utils.JsonUtils;
import cyou.mayloves.bond.utils.JwtUtils;
import cyou.mayloves.bond.utils.Result;
import cyou.mayloves.bond.utils.UserIdContextHolder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

/**
 * 鉴权拦截器
 *
 * @author SeanRon.Wong
 * @version 1.0.0
 * @since 2024/11/16 13:50
 */
@Slf4j
public class AuthInterceptor implements HandlerInterceptor {

    private static void authFail(HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        Result<Object> result = Result.error("Invalid or expired token.");
        result.setCode(401);
        response.getWriter().write(JsonUtils.toJsonString(result));
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization");
        if (!StringUtils.hasText(token)) {
            authFail(response);
            return false;
        }
        token = token.replace("Bearer ", "");
        Integer userId = JwtUtils.getUserIdFromToken(token);
        if (userId == null) {
            authFail(response);
            return false;
        }
        UserIdContextHolder.setUserId(userId);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        UserIdContextHolder.removeUserId();
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        UserIdContextHolder.removeUserId();
    }
}
