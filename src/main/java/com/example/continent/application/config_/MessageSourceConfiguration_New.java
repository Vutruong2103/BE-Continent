package com.example.continent.application.config_;

import com.example.continent.application.constants.Constants;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.util.Locale;

/**
 * @author : TruongTn
 * @created : 09/10/2024, Wed
 * @since : 0.0.1
 * 
 * @Note :{ 
 *  Cấu hình i18n (đa ngôn ngữ) tối ưu cho REST API Spring Boot.
 * - Không dùng session, không cần interceptor.
 * }
 */
@Configuration
public class MessageSourceConfiguration_New {

    /**
     * Cấu hình nguồn thông điệp đa ngôn ngữ.
     * Đọc file message từ classpath:i18n/messages, mã hóa UTF-8, tự động reload mỗi 1 giờ.
     * 
     * @return MessageSource cấu hình sẵn.
     */
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource =
                new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:i18n/messages");
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setCacheSeconds(3600); // reload mỗi giờ
        return messageSource;
    }

    /**
     * Sử dụng AcceptHeaderLocaleResolver để lấy locale từ header "Accept-Language".
     * Điều này phù hợp với các ứng dụng RESTful, không giữ trạng thái (stateless).
     * Không cần dùng session hoặc cookie để lưu trữ thông tin locale.
     * @return LocaleResolver cấu hình sẵn locale mặc định.
     */
    @Bean
    public LocaleResolver localeResolver() {
        AcceptHeaderLocaleResolver resolver = new AcceptHeaderLocaleResolver();
        resolver.setDefaultLocale(new Locale(Constants.DEFAULT_LANGUAGE));
        return resolver;
    }
}