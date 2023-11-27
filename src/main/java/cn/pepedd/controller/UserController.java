package cn.pepedd.controller;

import cn.pepedd.annotation.VerifyCaptcha;
import cn.pepedd.entity.dto.RegisterBodyDTO;
import cn.pepedd.entity.result.Result;
import cn.pepedd.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * TODO
 *
 * @author pepedd864
 * @since 2023/12/4
 */
@RestController
@RequestMapping("/user")
public class UserController {
  @Autowired
  private IUserService userService;

  /**
   * 注册
   *
   * @param registerBodyDTO 注册信息
   * @return 登录消息
   */
  @PostMapping("/register")
  public Result register(@RequestBody @Valid RegisterBodyDTO registerBodyDTO) {

    return userService.register(registerBodyDTO);
  }

  @GetMapping("/test")
  @VerifyCaptcha(code = "0", uuid = "1")
  public Result test(String code, String uuid) {
    return Result.success();
  }
}
