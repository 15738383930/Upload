package com.example.upload.config;


import com.example.upload.utils.CommUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class WebConfig extends WebMvcConfigurationSupport {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/files/**").addResourceLocations("file:" + CommUtil.Method.getPropertiesByKey(CommUtil.Property.PROBJECT_PROPERTIES, CommUtil.Property.FILE_UPLOAD_URL));
        //file:/opt/plate/指向本地图片路径地址
        super.addResourceHandlers(registry);
    }
}
