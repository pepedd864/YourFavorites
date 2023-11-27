package cn.pepedd.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * TODO
 *
 * @author pepedd864
 * @since 2023/12/6
 */
@Configuration // 一定不要忽略此注解
public class CorsConfig implements WebMvcConfigurer {
  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**") // 所有接口
        .allowCredentials(true) // 是否发送 Cookie
        .allowedOriginPatterns("*") // 支持域
        .allowedMethods(new String[]{"GET", "POST", "PUT", "DELETE"}) // 支持方法
        .allowedHeaders("*")
        .exposedHeaders("*");
  }
}
