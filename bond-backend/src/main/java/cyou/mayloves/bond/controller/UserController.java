package cyou.mayloves.bond.controller;


import cyou.mayloves.bond.entity.User;
import cyou.mayloves.bond.service.UserService;
import cyou.mayloves.bond.utils.Result;
import cyou.mayloves.bond.utils.UserIdContextHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/tag")
    public Result<List<String>> listTag() {
        Integer userId = UserIdContextHolder.getUserId();
        User user = userService.getById(userId);
        String tags = user.getTags();
        return StringUtils.hasText(tags) ? Result.success(Arrays.stream(tags.split(",")).toList()) : Result.success(Collections.emptyList());
    }

    @PostMapping("/tag")
    public Result<String> appendTag(@RequestParam String tag) {
        Integer userId = UserIdContextHolder.getUserId();
        User user = userService.getById(userId);
        String tags = StringUtils.hasText(user.getTags()) ? user.getTags() : "";
        List<String> tagList = new ArrayList<>(Arrays.asList(tags.split(",")));
        tagList.add(tag);
        user.setTags(String.join(",", tagList));
        userService.updateById(user);
        return Result.success();
    }

}
