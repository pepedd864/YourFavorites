package cn.pepedd.entity.result;

import cn.hutool.json.JSONUtil;
import lombok.Data;

import java.io.Serializable;

/**
 * 统一返回类
 *
 * @author pepedd864
 * @since 2023/8/22 8:33
 */
@Data
public class Result<T> implements Serializable {

  public static final int SUCCESS = 200;
  public static final int ERROR = 500;

  private Integer code; //编码：1成功，0和其它数字为失败
  private String msg; //错误信息
  private T data; //数据

  public Result() {
    this.code = 1;
  }

  public static <T> Result<T> success() {
    Result<T> result = new Result<T>();
    result.code = 1;
    return result;
  }

  public static <T> Result<T> success(T object) {
    Result<T> result = new Result<T>();
    result.data = object;
    result.code = SUCCESS;
    return result;
  }

  public static <T> Result<T> error(String msg) {
    Result result = new Result();
    result.msg = msg;
    result.code = ERROR;
    return result;
  }

  public static void main(String[] args) {
    Result result = new Result();
    result.code = 1;
    result.msg = "success";
    result.data = "data";
    System.out.println(result);
  }

  /**
   * 重写toString方法, 默认返回json字符串
   *
   * @return
   */
  @Override
  public String toString() {
    return JSONUtil.toJsonStr(this);
  }
}

