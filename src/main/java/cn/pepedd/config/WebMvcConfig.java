package cn.pepedd.config;

import cn.pepedd.json.JacksonObjectMapper;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

/**
 * Spring MVC配置类
 *
 * @author pepedd864
 * @since 2023/11/25
 */
public class WebMvcConfig extends WebMvcConfigurationSupport {


  /**
   * 拓展Spring MVC消息转换器
   * 处理一些特殊的返回值在序列化时的问题
   *
   * @param converters
   */
  protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
    MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
    converter.setObjectMapper(new JacksonObjectMapper());
    converters.add(0, converter);
  }
}
