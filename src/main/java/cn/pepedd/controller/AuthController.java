package cn.pepedd.controller;

import cn.hutool.core.util.IdUtil;
import cn.pepedd.constants.CacheConstants;
import cn.pepedd.constants.Constants;
import cn.pepedd.entity.dto.LoginBodyDTO;
import cn.pepedd.entity.result.Result;
import cn.pepedd.redis.RedisCache;
import cn.pepedd.service.IUserService;
import com.google.code.kaptcha.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static cn.pepedd.constants.Constants.CAPTCHA_ENABLED;

/**
 * 登录控制器
 *
 * @author pepedd864
 * @since 2023/11/24
 */
@RestController
@RequestMapping("/auth")
public class AuthController {
  @Resource(name = "captchaProducer")
  private Producer captchaProducer;

  @Resource(name = "captchaProducerMath")
  private Producer captchaProducerMath;

  @Autowired
  private RedisCache redisCache;

  @Autowired
  private IUserService userService;

  /**
   * 登录
   *
   * @param loginBodyDto 登录信息
   * @return 登录结果
   */
  @PostMapping("/login")
  public Result login(@RequestBody @Valid LoginBodyDTO loginBodyDto) {
    // 延时
    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return userService.login(loginBodyDto);
  }

  /**
   * 生成验证码
   *
   * @return 验证码
   */
  @GetMapping("/captchaImage")
  public Result captchaImage() {
    Map<String, Object> captcha = new HashMap<>();

    // 读取配置文件是否开启验证码，这里默认开启
    // boolean captchaEnabled = configService.selectCaptchaEnabled();
    boolean captchaEnabled = CAPTCHA_ENABLED;
    captcha.put("captchaEnabled", captchaEnabled);
    if (!captchaEnabled) {
      return Result.success(captcha);
    }

    // 生成唯一id存入redis，后续校验可以取出使用
    String uuid = IdUtil.simpleUUID();
    String verifyKey = CacheConstants.CAPTCHA_CODE_KEY + uuid;

    String capStr = null, code = null;
    BufferedImage image = null;

    // 生成验证码 默认是计算类型的验证码 可以为传统的字符模式
    String captchaType = "math";
    if ("math".equals(captchaType)) {
      String capText = captchaProducerMath.createText();
      capStr = capText.substring(0, capText.lastIndexOf("@"));
      code = capText.substring(capText.lastIndexOf("@") + 1);
      image = captchaProducerMath.createImage(capStr);
    } else if ("char".equals(captchaType)) {
      capStr = code = captchaProducer.createText();
      image = captchaProducer.createImage(capStr);
    }

    redisCache.setCacheObject(verifyKey, code, Constants.CAPTCHA_EXPIRATION, TimeUnit.MINUTES); // 过期时间2分钟
    // 转换流信息写出
    FastByteArrayOutputStream os = new FastByteArrayOutputStream();
    try {
      ImageIO.write(image, "jpg", os);
    } catch (IOException e) {
      return Result.error(e.getMessage());
    }

    captcha.put("uuid", uuid);
    captcha.put("img", os.toByteArray());
    return Result.success(captcha);
  }
}
