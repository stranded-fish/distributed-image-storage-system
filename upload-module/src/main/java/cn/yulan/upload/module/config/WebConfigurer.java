package cn.yulan.upload.module.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 配置静态资源映射
 *
 * @author Yulan Zhou
 */
@Configuration
public class WebConfigurer implements WebMvcConfigurer {

    @Value("${multipartFile.base-dir}")
    private String baseDir;

    /**
     * 将所有以 /images/** 开头的资源请求，映射到本地图片保存的基地址
     *
     * @author Yulan Zhou
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:" + baseDir);
    }

}