package com.foo.config;

import com.ajaxjs.data.jdbc_helper.JdbcConn;
import com.ajaxjs.data.jdbc_helper.JdbcReader;
import com.ajaxjs.data.jdbc_helper.JdbcWriter;
import com.ajaxjs.framework.BaseWebMvcConfigure;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Configuration
@Slf4j
public class FooConfig implements WebMvcConfigurer {
    @Value("${db.url}")
    private String url;

    @Value("${db.user}")
    private String user;

    @Value("${db.psw}")
    private String psw;

//    @Bean(value = "dataSource", destroyMethod = "close")
//    DataSource getDs() {
//        return JdbcConn.setupMysqlJdbcPool(url, user, psw);
//    }

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

    @Bean(value = "dataSource", destroyMethod = "close")
    public DataSource dataSource() throws SQLException {
        String jdbcStr = "jdbc:derby:C:\\Users\\zx\\Downloads\\db-derby-10.14.2.0-bin\\lib\\myDatabase;create=false";
        DataSource dataSource = JdbcConn.setupJdbcPool("org.apache.derby.jdbc.EmbeddedDriver", jdbcStr, "", "");

    //        try (Connection conn = dataSource.getConnection()) {
    //            JdbcReader reader = new JdbcReader();
    //            reader.setConn(conn);
    //            List<Map<String, Object>> list = reader.queryAsMapList("SELECT * FROM Employees");
    //            System.out.println(list);
    //        }

        return dataSource;
    }

//    @Bean
//    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
//    BaseCRUD_Service<?> getCRUD_Service() {
//        BaseCRUD_Service<?> crud = new BaseCRUD_Service<>();
//        crud.setReader(jdbcReader());
//        crud.setWriter(jdbcWriter());
//
//        return crud;
//    }

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
