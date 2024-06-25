package com.foo.config;

import com.ajaxjs.data.CRUD_Service;
import com.ajaxjs.data.jdbc_helper.JdbcConn;
import com.ajaxjs.data.jdbc_helper.JdbcReader;
import com.ajaxjs.data.jdbc_helper.JdbcWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.ajaxjs.framework.spring.BaseWebMvcConfigure;

import javax.sql.DataSource;

@Configuration
@Slf4j
public class FooConfig implements WebMvcConfigurer {
    @Value("${db.url}")
    private String url;

    @Value("${db.user}")
    private String user;

    @Value("${db.psw}")
    private String psw;

    @Bean(value = "dataSource", destroyMethod = "close")
    DataSource getDs() {
        return JdbcConn.setupMysqlJdbcPool(url, user, psw);
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public JdbcWriter jdbcWriter() {
        JdbcWriter jdbcWriter = new JdbcWriter();
        jdbcWriter.setIdField("id");
        jdbcWriter.setIsAutoIns(true);
        jdbcWriter.setConn(JdbcConn.getConnection());
        log.info("get new connection");

        return jdbcWriter;
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public JdbcReader jdbcReader() {
        JdbcReader jdbcReader = new JdbcReader();
        jdbcReader.setConn(JdbcConn.getConnection());
        log.info("get new connection-jdbcReader");

        return jdbcReader;
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    CRUD_Service<?> getCRUD_Service() {
        CRUD_Service<?> crud = new CRUD_Service<>();
        crud.setReader(jdbcReader());
        crud.setWriter(jdbcWriter());

        return crud;
    }

    /**
     * 跨域
     *
     * @param registry 注册跨域
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        BaseWebMvcConfigure.allowCrossDomain(registry);
    }
}
