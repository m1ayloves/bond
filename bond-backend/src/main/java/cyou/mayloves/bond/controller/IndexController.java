package cyou.mayloves.bond.controller;

import cyou.mayloves.bond.utils.Result;
import cyou.mayloves.bond.utils.ServletUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author SeanRon.Wong
 * @version 1.0.0
 * @since 2024/11/21 16:15
 */

@RestController
@RequestMapping("/")
public class IndexController {

    @GetMapping
    public Result<Map<String, Object>> index() {
        Map<String, Object> data = new HashMap<>();
        data.put("clientIP", ServletUtils.getClientIP());
        data.put("headers", ServletUtils.getRequestHeaders());
        Result<Map<String, Object>> result = Result.success(data);
        result.setMsg("success");
        return result;
    }

}
