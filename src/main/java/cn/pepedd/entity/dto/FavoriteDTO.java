package cn.pepedd.entity.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * TODO
 *
 * @author pepedd864
 * @since 2023/12/1
 */
@Data
public class FavoriteDTO implements Serializable {
  private Integer id;
  @NotBlank(message = "标签id不能为空")
  private Integer tagId;
  private String title;
  private String desc;
  private String url;
  private Integer level;
  private Integer index;
  private String key;
  private LocalDateTime createTime;
  private LocalDateTime updateTime;
  private String remark;
}
