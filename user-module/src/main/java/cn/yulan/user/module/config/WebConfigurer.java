package cn.yulan.user.module.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 映射
 *
 * @author Yulan Zhou
 * @date 2021/03/12
 */
@Configuration
public class WebConfigurer implements WebMvcConfigurer {

    /**
     * 图片保存路径，自动从yml文件中获取数据
     * 示例： E:/images/
     */
    private String fileSavePath = "E:/images/";

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        /**
         * 配置资源映射
         * 意思是：如果访问的资源路径是以“/images/”开头的，
         * 就给我映射到本机的“E:/images/”这个文件夹内，去找你要的资源
         * 注意：E:/images/ 后面的 “/”一定要带上
         */
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:" + fileSavePath);
    }

}