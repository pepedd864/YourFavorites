package cn.pepedd.entity.vo;

import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户vo
 *
 * @author pepedd864
 * @since 2023/9/14 9:08
 */
@Data
@Builder
public class UserVO implements Serializable {
  private String username;
  private String nickname;
  private String phone;
  private String email;
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime createTime;
}
