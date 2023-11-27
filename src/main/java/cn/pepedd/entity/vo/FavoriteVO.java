package cn.pepedd.entity.vo;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 收藏夹项vo
 *
 * @author pepedd864
 * @since 2023/12/1
 */
@Data
@Builder
public class FavoriteVO implements Serializable {
  private Integer id;
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
