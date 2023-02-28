package com.ren.reggie.config;

import com.ren.reggie.common.JacksonObjectMapper;
import com.ren.reggie.interceptor.LoginCheckInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

/**
 * @program: reggie_take_out
 * @author: Ren  https://github.com/machsh64
 * @create: 2023-02-27 16:12
 * @description:
 **/
@Slf4j
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

    @Value("${isTestInfo}")
    private Boolean isTestInfo;

    @Autowired
    private LoginCheckInterceptor loginCheckInterceptor;

    // 不需要登录拦截的url:登录注册和验证码
    final String[] notLoginInterceptPaths = {"/login.html","/employee/login","/employee/logout","/backend/**","/front/**"};//"/", "/login/", "/person/", "/register/", "/validcode", "/captchaCheck", "/file/", "/contract/htmltopdf", "/questions/", "/payLog/", "/error/" };

    /**
     * 设置静态资源映射
     * 因为spring默认不允许直接访问 templates下的文件，所以在部署后应该关闭该映射
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        if (isTestInfo) {
            log.info("# 开启静态资源映射 #");
            //这里是指在url上面打的内容
            registry.addResourceHandler("/backend/**")
                    //下面的是指可以对应resources文件下那些内容
                    .addResourceLocations("classpath:/templates/backend/")
                    .addResourceLocations("classpath:/static/backend/");
            registry.addResourceHandler("/front/**")
                    //下面的是指可以对应resources文件下那些内容
                    .addResourceLocations("classpath:/templates/front/")
                    .addResourceLocations("classpath:/static/front/");
        }
        // 解决静态资源无法访问
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
        // 解决swagger无法访问
        registry.addResourceHandler("/swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        // 解决swagger的js文件无法访问
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
     //   registry.addInterceptor(loginCheckInterceptor)
     //           .addPathPatterns("/*")
     //           .excludePathPatterns(notLoginInterceptPaths);
    }

    /**
     * 扩展mvc框架的消息抓换器
     * @param converters
     */
    @Override
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        // 创建一个新的消息转换器对象
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
        // 设置对象转换器，底层使用Jackson将java对象转为json
        messageConverter.setObjectMapper(new JacksonObjectMapper());
        //将上面的消息转化器对象追加到mvc框架的转换器中，下标设置为0保证转换器的优先度
        converters.add(0,messageConverter);
    }
}
