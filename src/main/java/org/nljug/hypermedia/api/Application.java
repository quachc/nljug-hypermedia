package org.nljug.hypermedia.api;

import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import com.theoryinpractise.halbuilder.standard.StandardRepresentationFactory;
import org.glassfish.jersey.servlet.ServletContainer;
import org.glassfish.jersey.servlet.ServletProperties;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author chris
 */
@EnableAutoConfiguration
@ComponentScan("org.nljug.hypermedia.api")
public class Application {

    public static void main(String... args) {
        new SpringApplicationBuilder(Application.class)
                .showBanner(false)
                .logStartupInfo(false)
                .run(args);
    }

    @Bean
    public RepresentationFactory representationFactory() {
        return new StandardRepresentationFactory()
                .withFlag(RepresentationFactory.PRETTY_PRINT)
                .withFlag(RepresentationFactory.STRIP_NULLS)
                .withFlag(RepresentationFactory.COALESCE_LINKS);
    }

    @Bean
    public ServletRegistrationBean jerseyServlet() {
        ServletRegistrationBean registration = new ServletRegistrationBean(new ServletContainer(), "/hypermedia/*");
        registration.addInitParameter(ServletProperties.JAXRS_APPLICATION_CLASS, JerseyConfig.class.getName());
//        registration.addInitParameter("com.sun.jersey.api.json.POJOMappingFeature", "true");
        return registration;
    }
}
