package edu.tum.ase.frontend;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

import java.io.IOException;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Taken from https://stackoverflow.com/questions/46148843/spring-boot-angular-4-routing-in-app-hits-the-server
        // These patterns ensure that everything is routed to the static Angular index.html (or the other files, see below)
        // The second pattern is probably not needed because the UI only exists under /ui/ but it also does not cause any harm
        // since requests are already filtered by the gateway
        registry.addResourceHandler("/ui/**/*", "/**/*")
                .addResourceLocations("classpath:/static/")
                .resourceChain(true)
                .addResolver(new PathResourceResolver() {
                    @Override
                    protected Resource getResource(String resourcePath, Resource location) throws IOException {
                        Resource requestedResource = location.createRelative(resourcePath);
                        // If a file that exists is requested return it, otherwise return index.html and let Angular do its magic
                        return requestedResource.exists() && requestedResource.isReadable() ? requestedResource : new ClassPathResource("/static/index.html");
                    }
                });
    }
}