package cn.pepedd.entity.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 登录请求体
 *
 * @author pepedd864
 * @since 2023/11/24
 */
@Data
public class LoginBodyDTO implements Serializable {
  @NotBlank(message = "用户名不能为空")
  private String username;
  @NotBlank(message = "密码不能为空")
  private String password;
  @NotBlank(message = "验证码不能为空")
  private String code;
  @NotBlank(message = "验证码uuid不能为空")
  private String uuid;
  private boolean rememberMe;
}
