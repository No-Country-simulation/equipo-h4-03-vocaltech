package com.vocaltech.api.template;


import com.vocaltech.api.service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.thymeleaf.IEngineConfiguration;
import org.thymeleaf.templateresolver.AbstractConfigurableTemplateResolver;
import org.thymeleaf.templateresource.ITemplateResource;
import org.thymeleaf.templateresource.StringTemplateResource;
import java.util.Map;

public class S3TemplateResolver extends AbstractConfigurableTemplateResolver {

    // Inyecta el S3Service o configura lo que necesites para acceder a S3
    private final S3Service s3Service;

    @Autowired
    public S3TemplateResolver(S3Service s3Service) {
        this.s3Service = s3Service;
    }


       @Override
    protected ITemplateResource computeTemplateResource(IEngineConfiguration configuration, String ownerTemplate, String template, String resourceName, String characterEncoding, Map<String, Object> templateResolutionAttributes) {
        // En este caso, "resourceName" se supone es la clave del template en S3.
        // Llama a un método que descargue el contenido del template desde S3 usando esa clave.
        String templateContent = s3Service.downloadTemplateAsString(resourceName);

        // Si no se encontró el contenido o es nulo, se podría retornar null o lanzar una excepción.
        if (templateContent.isEmpty()) {
            throw new IllegalStateException("No se pudo descargar el template para la clave: " + resourceName);
        }

        // Retorna un recurso de template a partir del contenido obtenido.
        return new StringTemplateResource(templateContent);
    }


}


