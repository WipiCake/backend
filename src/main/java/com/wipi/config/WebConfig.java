package com.wipi.config;

import com.wipi.inferfaces.api.resolver.LoginUserDetailsResolver;
import com.wipi.support.properties.ImagesPathProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import java.util.List;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(ImagesPathProperties.class)
public class WebConfig implements WebMvcConfigurer {

    private final LoginUserDetailsResolver loginUserDetailsResolver;
    private final ImagesPathProperties imagesPathProperties;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(loginUserDetailsResolver);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(imagesPathProperties.getSrc())
                .addResourceLocations(imagesPathProperties.getPath());
    }
}
