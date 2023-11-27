package cn.pepedd.entity.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户实体类
 *
 * @author pepedd864
 * @since 2023/11/24
 */
@Data
@Builder
@TableName(value = "sys_user", autoResultMap = true)
public class User implements Serializable {
  private Integer id;
  private String username;
  private String nickname;
  private String password;
  private String phone;
  private String email;
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @TableField(fill = FieldFill.INSERT)
  private LocalDateTime createTime;
}
