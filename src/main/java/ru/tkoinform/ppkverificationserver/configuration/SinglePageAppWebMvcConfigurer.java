package ru.tkoinform.ppkverificationserver.configuration;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

import java.io.IOException;

@Configuration
public class SinglePageAppWebMvcConfigurer implements WebMvcConfigurer {

    @Autowired
    private ResourceProperties resourceProperties;

    @Autowired
    private RequestsInterceptor requestsInterceptor;

    private String apiPath = "/api";

    public SinglePageAppWebMvcConfigurer() {
    }

    public SinglePageAppWebMvcConfigurer(String apiPath) {
        this.apiPath = apiPath;
    }

    protected String getApiPath() {
        return apiPath;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/")
                //.addResourceLocations(resourceProperties.getStaticLocations())
                //.setCachePeriod(resourceProperties.getCache().getPeriod().getNano())
                .resourceChain(true)
                .addResolver(new SinglePageAppResourceResolver());
    }

    private class SinglePageAppResourceResolver extends PathResourceResolver {
        @Override
        protected Resource getResource(String resourcePath, Resource location) throws IOException {
            //logger.info("PATH " + resourcePath);
            Resource resource = location.createRelative(resourcePath);
            if (resource.exists() && resource.isReadable()) {
                return resource;
            } else if (getApiPath() != null && ("/" + resourcePath).startsWith(getApiPath())) {
                return null;
            } else {
                LoggerFactory.getLogger(getClass()).info("Routing /" + resourcePath + " to /index.html");
                resource = location.createRelative("index.html");
                if (resource.exists() && resource.isReadable()) {
                    return resource;
                } else {
                    return null;
                }
            }
        }
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requestsInterceptor)
                .addPathPatterns("/api/**/");
    }
}
