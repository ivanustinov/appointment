package ru.ustinov.appointment.config;

/**
 * //TODO add comments.
 *
 * @author Ivan Ustinov(ivanustinov1985@yandex.ru)
 * @version 1.0
 * @since 29.01.2024
 */

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.datatype.hibernate6.Hibernate6Module;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@EnableWs
@Configuration
public class WebServiceConfig extends WsConfigurerAdapter {

    @Bean
    public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(ApplicationContext applicationContext) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean<>(servlet, "/ws/*");
    }

    @Bean(name = "schedule")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema scheduleSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("AppointmentPort");
        wsdl11Definition.setLocationUri("/ws");
        wsdl11Definition.setTargetNamespace("http://appointment/schedule-web-service");
        wsdl11Definition.setSchema(scheduleSchema);
        return wsdl11Definition;
    }

    @Bean
    public XsdSchema scheduleSchema() {
        return new SimpleXsdSchema(new ClassPathResource("schedule.xsd"));
    }

    @Bean
    Module module() {
        return new Hibernate6Module()
                .configure(Hibernate6Module.Feature.SERIALIZE_IDENTIFIER_FOR_LAZY_NOT_LOADED_OBJECTS, false);
    }

}

