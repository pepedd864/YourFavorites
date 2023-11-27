package cn.pepedd.service;

import cn.pepedd.entity.dto.RegisterBodyDTO;
import cn.pepedd.entity.pojo.User;
import cn.pepedd.entity.result.Result;

/**
 * TODO
 *
 * @author pepedd864
 * @since 2023/11/24
 */
public interface IUserService extends IAuthService<User> {
  Result register(RegisterBodyDTO registerBodyDTO);
}
