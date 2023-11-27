package cn.pepedd.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO
 *
 * @author pepedd864
 * @since 2023/11/24
 */
@RestController
public class IndexController {
  @SaCheckLogin
  @RequestMapping("/")
  public String index() {
    return "通过前端访问服务~";
  }
}
