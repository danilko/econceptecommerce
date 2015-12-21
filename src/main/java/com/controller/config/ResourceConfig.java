
/**
 * The MIT License (MIT)
 * <p/>
 * Copyright (c) 2013 Kai-Ting (Danil) Ko
 * <p/>
 * Permission is hereby granted, free of charge,
 * to any person obtaining a copy of this software
 * and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify,
 * merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom
 * the Software is furnished to do so, subject to the
 * following conditions:
 * <p/>
 * The above copyright notice and this permission notice
 * shall be included in all copies or substantial portions of the Software.
 * <p/>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY
 * OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
 * TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
 * OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE
 * USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.controller.config;

import java.util.Arrays;

import javax.ws.rs.core.Application;
import javax.ws.rs.ext.RuntimeDelegate;

import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.controller.resource.PaymentResource;
import com.controller.resource.ProductResource;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.jaxrs.listing.ApiListingResource;
import io.swagger.jaxrs.listing.SwaggerSerializers;

@Configuration
public class ResourceConfig {
    @Bean(destroyMethod = "shutdown")

    /**
     * Need to be declared as "cxf" bean name to be able to register successfully
     * Do not change the name
     */
    public SpringBus cxf() {
        return new SpringBus();
    }

    @Bean
    public JacksonJsonProvider getJacksonJsonProvider() {
        return new JacksonJsonProvider();
    }

    @Bean
    public ProductResource getProductResource() {
        return new ProductResource();
    }

    @Bean
    public PaymentResource getPaymentResource() {
        return new PaymentResource();
    }

    @Bean
    public SwaggerSerializers getSwaggerSeralizers() {
        return new SwaggerSerializers();
    }

    @Bean
    public ApiListingResource getSwaggerApiListingResource() {
        return new ApiListingResource();
    }

    @Bean
    public BeanConfig getSwaggerConfig() {
        BeanConfig beanConfig = new BeanConfig();
        beanConfig.setResourcePackage("com.controller.resource");
        beanConfig.setVersion("1.0.0");
        beanConfig.setBasePath("/controller/rest");
        beanConfig.setTitle("Controller API");
        beanConfig.setScan(true);

        return beanConfig;
    }

    @Bean
    public Server initJAXRSServer() {

        JAXRSServerFactoryBean factory = RuntimeDelegate.getInstance().createEndpoint(new Application(), JAXRSServerFactoryBean.class);

        factory.setAddress(factory.getAddress());

        factory.setServiceBean(Arrays.<Object>asList(getProductResource(), getPaymentResource(), getSwaggerApiListingResource()));

        factory.setProviders(Arrays.<Object>asList(getJacksonJsonProvider(), getSwaggerSeralizers()));

        return factory.create();
    }
}
