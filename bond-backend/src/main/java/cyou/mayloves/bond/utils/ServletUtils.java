package cyou.mayloves.bond.utils;

import cn.hutool.extra.servlet.JakartaServletUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Servlet 相关工具类封装
 *
 * @author SeanRon.Wong
 * @version 1.0.0
 * @since 2024/11/21 16:25
 */
public class ServletUtils extends JakartaServletUtil {

    /**
     * 获取当前线程中的 HttpServletRequest 对象
     * <p>
     * 此方法用于在 Spring 框架中获取当前请求的 HttpServletRequest 对象它利用了 Spring 的 RequestContextHolder 工具类，
     * 结合当前线程的请求属性，以非侵入性的方式获取请求对象这种方法在需要访问HTTP请求属性时特别有用，
     * 尤其是在没有直接访问到 HttpServletRequest 对象的情况下
     *
     * @return 当前线程中的 HttpServletRequest 对象
     */
    public static HttpServletRequest getHttpServletRequest() {
        return ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
    }

    /**
     * 获取客户端 IP 地址
     *
     * @return 客户端 IP 地址
     */
    public static String getClientIP() {
        return getClientIP(getHttpServletRequest());
    }

    /**
     * 获取 HTTP 请求的所有头部信息
     * <p>
     * 此方法旨在收集当前 HTTP 请求的所有头部信息，并将其存储在一个 Map 中
     * 它遍历请求的头部名称，并为每个头部名称获取对应的值，然后将这对键值对添加到 Map 中
     *
     * @return Map<String, String> 包含请求头部信息的Map，其中键是头部名称，值是对应的头部值
     */
    public static Map<String, String> getRequestHeaders() {
        // 获取当前的HTTP请求对象
        HttpServletRequest request = getHttpServletRequest();

        // 创建一个Map来存储请求的头部信息
        Map<String, String> headers = new HashMap<>();

        // 获取请求的所有头部名称
        Enumeration<String> headerNames = request.getHeaderNames();

        // 遍历头部名称，将每个头部的名称和值添加到Map中
        while (headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            String value = request.getHeader(name);
            headers.put(name, value);
        }

        // 返回包含所有请求头部信息的Map
        return headers;
    }

}
