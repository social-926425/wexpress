package me.iazure.ex.express.config.controller;


import me.iazure.ex.express.interceptor.AuthenticationInterceptor;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
// 等价于<mvc:annotation-driven/>
@EnableWebMvc
public class MvcConfiguration implements ApplicationContextAware, WebMvcConfigurer{

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * 静态资源配置
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/upload/**").addResourceLocations("file:/D:/projectdev/image/upload/");
    }

    /**
     * 自定义请求处理器
     * @param configurer
     */
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Bean
    public AuthenticationInterceptor getAuthenticationInterceptor(){
        return new AuthenticationInterceptor();
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        String url = "/**";
        InterceptorRegistration registration = registry.addInterceptor(getAuthenticationInterceptor());
        registration.addPathPatterns(url);

    }
}
