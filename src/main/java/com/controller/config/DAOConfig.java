/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2013 Kai-Ting (Danil) Ko
 * <p>
 * Permission is hereby granted, free of charge,
 * to any person obtaining a copy of this software
 * and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify,
 * merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom
 * the Software is furnished to do so, subject to the
 * following conditions:
 * <p>
 * The above copyright notice and this permission notice
 * shall be included in all copies or substantial portions of the Software.
 * <p>
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

import com.controller.dao.ProductDAO;
import com.controller.dao.com.controller.dao.impl.spring.SpringProductDAO;
import com.controller.dao.com.controller.dao.impl.spring.SpringProductRowMapper;
import com.controller.dao.com.controller.dao.impl.spring.SpringProductSpecificationRowMapper;
import com.zaxxer.hikari.HikariDataSource;
import liquibase.integration.spring.SpringLiquibase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.inject.Inject;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@PropertySource(value = "classpath:application.properties")
public class DAOConfig {

    // Inject PropertySource into environment instance
    @Inject
    private Environment mEnvironment;

    @Bean
    public ProductDAO productDAO() {
        return new SpringProductDAO();
    }

    @Bean
    public SpringProductRowMapper springProductRowMapper() {
        return new SpringProductRowMapper();
    }

    @Bean
    public SpringProductSpecificationRowMapper springProductSpecificationRowMapper() {
        return new SpringProductSpecificationRowMapper();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean getEntityManagerFactoryBean() {
        LocalContainerEntityManagerFactoryBean lBean = new LocalContainerEntityManagerFactoryBean();

        lBean.setDataSource(dataSource());

        lBean.setPersistenceProviderClass(org.datanucleus.api.jpa.PersistenceProviderImpl.class);

        lBean.setPackagesToScan(new String[]{"com.controller.dao.impl.spring"});

        lBean.setLoadTimeWeaver(new InstrumentationLoadTimeWeaver());

        Properties japProperties = new Properties();

        japProperties.put("javax.persistence.jdbc.driver",
                mEnvironment.getProperty("DATABASE_DRIVER_NAME"));

        lBean.setJpaProperties(japProperties);
        lBean.afterPropertiesSet();
        return lBean;
    }


    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }

    @Bean
    public PlatformTransactionManager getTransactionManager() {
        JpaTransactionManager lTransactionManager = new JpaTransactionManager();
        lTransactionManager.setEntityManagerFactory(getEntityManagerFactoryBean().getObject());
        return lTransactionManager;
    }

    @Bean
    public HikariDataSource dataSource() {
        HikariDataSource lDataSource = new HikariDataSource();
        lDataSource.setDriverClassName(mEnvironment.getProperty("DATABASE_DRIVER_NAME"));
        lDataSource.setJdbcUrl(mEnvironment.getProperty("DATABASE_DRIVER_URL"));
        lDataSource.setUsername(mEnvironment.getProperty("DATABASE_DRIVER_USERNAME"));
        lDataSource.setPassword(mEnvironment.getProperty("DATABASE_DRIVER_PASSWORD"));

        return lDataSource;
    }  // DriverManagerDataSource getDataSource

    @Bean
    public SpringLiquibase liquibase() {
        SpringLiquibase liquibase = new SpringLiquibase();

        liquibase.setDataSource(dataSource());
        liquibase.setChangeLog("classpath:db.changelog-sample.json");

        return liquibase;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }
}