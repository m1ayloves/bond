package cyou.mayloves.bond.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cyou.mayloves.bond.dto.LoginRequest;
import cyou.mayloves.bond.dto.RegisterRequest;
import cyou.mayloves.bond.entity.User;

public interface UserService extends IService<User> {

    boolean registerUser(RegisterRequest registerRequest);

    User authenticateUser(LoginRequest loginRequest);

}