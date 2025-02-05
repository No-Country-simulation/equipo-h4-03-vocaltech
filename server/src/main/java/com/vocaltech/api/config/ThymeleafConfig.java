package com.vocaltech.api.config;

import com.vocaltech.api.service.S3Service;
import com.vocaltech.api.template.S3TemplateResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.ITemplateResolver;

@Configuration
public class ThymeleafConfig {

    // Inyecta el S3Service que ya debe estar definido como bean en tu aplicación
    @Bean
    public ITemplateResolver templateResolver(S3Service s3Service) {
        S3TemplateResolver resolver = new S3TemplateResolver(s3Service);
        resolver.setPrefix("");       // Configura el prefijo si es necesario
        resolver.setSuffix(".html");    // Configura el sufijo para tus templates
        resolver.setCacheable(false);   // Puedes desactivar el caché en desarrollo
        return resolver;
    }

    @Bean
    public TemplateEngine templateEngine(ITemplateResolver templateResolver) {
        TemplateEngine engine = new TemplateEngine();
        engine.setTemplateResolver(templateResolver);
        return engine;
    }
}



