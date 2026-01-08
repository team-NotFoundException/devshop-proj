package org.example.shopping._core.config;

import lombok.RequiredArgsConstructor;
import org.example.shopping._core.interceptor.AdminInterceptor;
import org.example.shopping._core.interceptor.LoginInterceptor;
import org.example.shopping._core.interceptor.OwnerInterceptor;
import org.example.shopping._core.interceptor.SessionInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {
    private final LoginInterceptor loginInterceptor;
    private final SessionInterceptor sessionInterceptor;
    private final OwnerInterceptor ownerInterceptor;
    private final AdminInterceptor adminInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/order/**", "/payment/**", "/review/**", "/user/**", "/cart/**", "/product/**")
                .excludePathPatterns(
                        "/user/login", "/user/join", "/user/join/**", "/user/logout", "/user/username-check", "/detail", "/", "/css/**",
                        "/static/js/**", "/images/**", "/favicon.io", "/user/popup/**", "/h2-console/**"
                );

        registry.addInterceptor(sessionInterceptor)
                .addPathPatterns("/**");
        registry.addInterceptor(adminInterceptor)
                .addPathPatterns("/admin/**");

        registry.addInterceptor(ownerInterceptor)
                .addPathPatterns("/owner/**")
                .excludePathPatterns("/owner/login", "/owner/join", "/products/**");
    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:///D:shopImages/");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
