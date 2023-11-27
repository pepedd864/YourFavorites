package cn.pepedd.service;

import cn.pepedd.entity.dto.LoginBodyDTO;
import cn.pepedd.entity.result.Result;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * TODO
 *
 * @author pepedd864
 * @since 2023/11/24
 */
public interface IAuthService<T> extends IService<T> {
  /**
   * 登录
   *
   * @param loginBodyDto
   * @return
   */
  Result login(LoginBodyDTO loginBodyDto);
}
