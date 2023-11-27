package cn.pepedd.entity.vo;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * 收藏夹标签vo
 *
 * @author pepedd864
 * @since 2023/12/1
 */
@Data
@Builder
public class TagVO implements Serializable {
  private Integer id;
  private Integer parentId;
  private String title;
  private String desc;
  private Integer level;
  private Integer index;
  private String key;
  private String createTime;
  private String updateTime;
  private String remark;
}
