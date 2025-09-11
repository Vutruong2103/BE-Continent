package com.example.continent.application.config;

import com.example.continent.application.constants.Constants;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

/**
 * cấu hình i18n (đa ngôn ngữ) cho ứng dụng Spring Boot
 * messageSource():
 * đọc file message đa ngôn ngữ từ classpath:i18n/messages, dùng mã hóa UTF-8, tự động reload mỗi 1 giờ.
 * localeResolver():
 * xác định ngôn ngữ mặc định cho người dùng (lấy từ Constants.DEFAULT_LANGUAGE), lưu thông tin ngôn ngữ vào session.
 * localeChangeInterceptor():
 * lắng nghe tham số lang trên request, cho phép chuyển đổi ngôn ngữ động.
 * addInterceptors():
 * Đăng ký interceptor vừa tạo cho tất cả endpoint /api/**, giúp thay đổi ngôn ngữ qua tham số lang trên URL.
 *
 */
@Configuration
public class MessageSourceConfiguration implements WebMvcConfigurer {

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource =
                new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:i18n/messages");
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setCacheSeconds(3600); // reload mỗi giờ
        return messageSource;
    }

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        // Ngôn ngữ mặc định
        slr.setDefaultLocale(new Locale(Constants.DEFAULT_LANGUAGE));
        return slr;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        return lci;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor()).addPathPatterns("/api/**");
    }
}
