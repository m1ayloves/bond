package cyou.mayloves.bond.utils;

/**
 * 用户 ID 上下文持有者
 *
 * @author SeanRon.Wong
 * @version 1.0.0
 * @since 2024/11/16 14:05
 */
public class UserIdContextHolder {

    private static final ThreadLocal<Integer> userIdContext = new ThreadLocal<>();

    public static Integer getUserId() {
        return userIdContext.get();
    }

    public static void setUserId(Integer userId) {
        userIdContext.set(userId);
    }

    public static void removeUserId() {
        userIdContext.remove();
    }
}
