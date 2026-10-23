package main.java.edu.infosys.finCoreBankApplication.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Value("${app.frontend.base-url}")
    private String frontendBaseUrl;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(frontendBaseUrl)
                .allowedMethods(
                        "GET", "POST", "PUT",
                        "DELETE", "PATCH", "OPTIONS"
                )
                .allowCredentials(true);
    }
}