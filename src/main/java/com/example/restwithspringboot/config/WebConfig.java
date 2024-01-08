package com.example.restwithspringboot.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.restwithspringboot.serializationconverter.YamlJacksonHttpMessageConverter;

@Configuration
public class WebConfig implements WebMvcConfigurer{

    private static final MediaType MEDIA_TYPE_APPLICATION_YAML = MediaType.valueOf("application/x-yaml");

    @Value("${spring.graphql.cors.allowed-origin-patterns:default}")
    private String corsOriginPatterns = "";

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        // https://www.baeldung.com/spring-mvc-content-negotiation-json-xml
        // via EXTENCION. http://localhost:8080/api/person/v1.xml
        
        /*// via QUERY PARAM. http://localhost:8080/api/person/v1?mediaType=xml
       
        //aceita parâmetros
        configurer.favorParameter(true).parameterName("mediaType")
            .ignoreAcceptHeader(true).useRegisteredExtensionsOnly(false)
            .defaultContentType(MediaType.APPLICATION_JSON).mediaType("json", MediaType.APPLICATION_JSON)
            .mediaType("xml", MediaType.APPLICATION_XML);*/

        // via HEADER PARAM. http://localhost:8080/api/person/v1

        configurer.favorParameter(false)
            .ignoreAcceptHeader(false).useRegisteredExtensionsOnly(false)
            .defaultContentType(MediaType.APPLICATION_JSON).mediaType("json", MediaType.APPLICATION_JSON)
            .mediaType("xml", MediaType.APPLICATION_XML).mediaType("x-yaml", MEDIA_TYPE_APPLICATION_YAML);
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new YamlJacksonHttpMessageConverter());
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        var allowedOrigins = corsOriginPatterns.split(",");
        registry.addMapping("/**")
            //.allowedMethods("GET", "POST", "PUT")
            .allowedMethods("*")
            .allowedOrigins(allowedOrigins)
            .allowCredentials(true);
    }
    
}
