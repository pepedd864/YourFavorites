package cn.pepedd.service.impl;

import cn.dev33.satoken.secure.BCrypt;
import cn.dev33.satoken.stp.StpUtil;
import cn.pepedd.annotation.VerifyCaptcha;
import cn.pepedd.entity.dto.LoginBodyDTO;
import cn.pepedd.entity.dto.RegisterBodyDTO;
import cn.pepedd.entity.pojo.User;
import cn.pepedd.entity.result.Result;
import cn.pepedd.entity.vo.LoginVO;
import cn.pepedd.entity.vo.UserVO;
import cn.pepedd.mapper.UserMapper;
import cn.pepedd.redis.RedisCache;
import cn.pepedd.service.IUserService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户服务实现类
 *
 * @author pepedd864
 * @since 2023/11/24
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
  @Autowired
  private RedisCache redisCache;

  public static void main(String[] args) {
//    String pw_hash = "$2a$10$HI2nRLS.KC/A2AMHd/tPBufnVS2UOPRmkodfRyFH7v04UOjFfRNhG";
//    System.out.println(BCrypt.checkpw("R769UqF0M2", pw_hash));
    System.out.println(BCrypt.hashpw("123456", BCrypt.gensalt()));
    System.out.println(BCrypt.checkpw("123456", "$2a$10$rFEbLSe2n5O.sVeT80UTeetChNS2y1rEaxqycQA4WLxh6RRns.4M6"));
  }

  /**
   * 登录
   *
   * @param loginBodyDto 登录信息
   * @return 登录结果
   */
  @Override
  @VerifyCaptcha(code = "0$code", uuid = "0$uuid")
  public Result login(LoginBodyDTO loginBodyDto) {
    // 1. 校验密码 TODO 这里 mybatis-plus 3.4.3 使用jdk17会报错
    User user = getOne(Wrappers.<User>lambdaQuery().eq(User::getUsername, loginBodyDto.getUsername()));
    if (user == null) {
      throw new RuntimeException("用户不存在");
    }
    if (!BCrypt.checkpw(loginBodyDto.getPassword(), user.getPassword())) {
      throw new RuntimeException("密码错误");
    }

    // 2. 登录
    StpUtil.login(user.getId());

    String token = StpUtil.getTokenValue();

    // 3. 返回结果
    UserVO userVO = UserVO.builder()
        .username(user.getUsername())
        .email(user.getEmail())
        .phone(user.getPhone())
        .nickname(user.getNickname())
        .createTime(user.getCreateTime())
        .build();
    LoginVO loginVO = LoginVO.builder()
        .token(token)
        .userVO(userVO)
        .build();
    return Result.success(loginVO);
  }

  /**
   * 注册
   *
   * @param registerBodyDTO 注册信息
   * @return 注册结果
   */
  @Override
  @VerifyCaptcha(code = "0$code", uuid = "0$uuid")
  public Result register(RegisterBodyDTO registerBodyDTO) {
    // 1. 校验用户名是否存在
    User user = getOne(Wrappers.<User>lambdaQuery().eq(User::getUsername, registerBodyDTO.getUsername()));
    if (user != null) {
      throw new RuntimeException("用户名已存在");
    }

    // 2. 校验邮箱是否存在
    user = getOne(Wrappers.<User>lambdaQuery().eq(User::getEmail, registerBodyDTO.getEmail()));
    if (user != null) {
      throw new RuntimeException("邮箱已存在");
    }

    // 3. 校验手机号是否存在
    user = getOne(Wrappers.<User>lambdaQuery().eq(User::getPhone, registerBodyDTO.getPhone()));
    if (user != null) {
      throw new RuntimeException("手机号已存在");
    }

    // 4. 注册
    user = User.builder()
        .username(registerBodyDTO.getUsername())
        .nickname(registerBodyDTO.getNickname())
        .password(BCrypt.hashpw(registerBodyDTO.getPassword(), BCrypt.gensalt()))
        .phone(registerBodyDTO.getPhone())
        .email(registerBodyDTO.getEmail())
        .build();

    save(user);

    // 5. 登录
    StpUtil.login(user.getId());
    String token = StpUtil.getTokenValue();

    // 6. 返回结果
    UserVO userVO = UserVO.builder()
        .username(user.getUsername())
        .email(user.getEmail())
        .phone(user.getPhone())
        .nickname(user.getNickname())
        .createTime(user.getCreateTime())
        .build();

    LoginVO loginVO = LoginVO.builder()
        .token(token)
        .userVO(userVO)
        .build();
    return Result.success(loginVO);
  }

}
