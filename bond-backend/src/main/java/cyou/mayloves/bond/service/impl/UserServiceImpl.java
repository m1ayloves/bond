package cyou.mayloves.bond.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cyou.mayloves.bond.dto.LoginRequest;
import cyou.mayloves.bond.dto.RegisterRequest;
import cyou.mayloves.bond.entity.User;
import cyou.mayloves.bond.mapper.UserMapper;
import cyou.mayloves.bond.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public boolean registerUser(RegisterRequest registerRequest) {
        long count = count(
                Wrappers.lambdaQuery(User.class)
                        .eq(User::getUsername, registerRequest.getUsername())
        );
        // 检查用户名是否存在
        if (count > 0) {
            return false;
        }
        // 注册用户
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(DigestUtils.md5DigestAsHex(registerRequest.getPassword().getBytes())); // 密码加密
        user.setTags("Default");
        save(user);
        return true;
    }

    @Override
    public User authenticateUser(LoginRequest loginRequest) {
        User user = getOne(
                Wrappers.query(User.class)
                        .eq("BINARY username", loginRequest.getUsername()),
                false
        );
        if (user != null && user.getPassword().equals(DigestUtils.md5DigestAsHex(loginRequest.getPassword().getBytes()))) {
            return user;
        }
        return null;
    }

}