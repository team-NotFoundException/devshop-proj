package org.example.shopping._core.config;

import lombok.RequiredArgsConstructor;
import org.example.shopping._core.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {
    private final LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/order/**", "/payment/**", "/review/**", "/user/**", "/cart/**", "/product/**")
                .excludePathPatterns(
                        "login", "join", "logout", "/detail", "/", "/css/**",
                        "/js/**", "/images/**", "/favicon.io", "/h2-console/**"
                );
    }
}
