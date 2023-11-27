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
 * 收藏夹项实体类
 *
 * @author pepedd864
 * @since 2023/12/1
 */
@Data
@Builder
@TableName(value = "tb_favorite", autoResultMap = true)
public class Favorite implements Serializable {
  private Integer id;
  private Integer userId;
  private Integer tagId;
  private String title;
  private String desc;
  private String url;
  private Integer level;
  private Integer index;
  private String key;
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @TableField(fill = FieldFill.INSERT)
  private LocalDateTime createTime;
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @TableField(fill = FieldFill.UPDATE)
  private LocalDateTime updateTime;
  private String remark;
}
