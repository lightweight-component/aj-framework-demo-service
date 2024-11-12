package com.foo.config;

import com.ajaxjs.data.crud.CRUD_Service;
import com.ajaxjs.data.jdbc_helper.DatabaseVendor;
import com.ajaxjs.data.jdbc_helper.JdbcConn;
import com.ajaxjs.data.jdbc_helper.JdbcReader;
import com.ajaxjs.data.jdbc_helper.JdbcWriter;
import com.ajaxjs.framework.BaseWebMvcConfigure;
import com.ajaxjs.security.captcha.image.ICaptchaImageProvider;
import com.ajaxjs.security.captcha.image.KaptchaImage;
import com.ajaxjs.security.session.ISessionService;
import com.ajaxjs.security.session.ServletSession;
import com.foo.common.Utils;
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

//    @Bean
//    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
//    public JdbcWriter jdbcWriter() {
//        JdbcWriter jdbcWriter = new JdbcWriter();
//        jdbcWriter.setIdField("id");
//        jdbcWriter.setIsAutoIns(true);
//        jdbcWriter.setConn(JdbcConn.getConnection());
//        log.info("get new connection");
//
//        return jdbcWriter;
//    }
//
//    @Bean
//    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
//    public JdbcReader jdbcReader() {
//        JdbcReader jdbcReader = new JdbcReader();
//        jdbcReader.setConn(JdbcConn.getConnection());
//        log.info("get new connection-jdbcReader");
//
//        return jdbcReader;
//    }

    @Bean(value = "dataSource", destroyMethod = "close")
    public DataSource dataSource() throws SQLException {
        String jdbcStr = "jdbc:derby:" + Utils.mkdir() + ";create=true";
        DataSource dataSource = JdbcConn.setupJdbcPool("org.apache.derby.jdbc.EmbeddedDriver", jdbcStr, "", "");

        // 创建测试数据
        try (Connection conn = dataSource.getConnection()) {
            JdbcWriter writer = new JdbcWriter();
            writer.setConn(conn);
            writer.insert(Utils.CREATE_TABLE);
            writer.insert("INSERT INTO Employees (id, name, birthday, hire_date, department) VALUES (1, 'John', '1980-01-01', '2005-06-15', 'Finance')");
            writer.insert("INSERT INTO Employees (id, name, hire_date, department) VALUES (2, 'Jane Smith', '2010-08-23', 'Sales')");
            writer.insert("INSERT INTO Employees (id, name, birthday, hire_date, department) VALUES (3, 'Alice Johnson', '1990-05-20', '2015-01-10', 'IT')," +
                    "(4, 'Mike Brown', '1985-11-12', '2018-04-01', 'HR')");

            JdbcReader reader = new JdbcReader();
            reader.setConn(conn);
            List<Map<String, Object>> list = reader.queryAsMapList("SELECT * FROM Employees");
            System.out.println(list);
        }

        return dataSource;
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public CRUD_Service getCRUD_Service() throws SQLException {
        CRUD_Service crud = new CRUD_Service();

        Connection connection = dataSource().getConnection();

        JdbcWriter writer = new JdbcWriter();
        writer.setConn(connection);
        writer.setDatabaseVendor(DatabaseVendor.DERBY);

        JdbcReader reader = new JdbcReader();
        reader.setConn(connection);
        reader.setDatabaseVendor(DatabaseVendor.DERBY);

        crud.setWriter(writer);
        crud.setReader(reader);

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

    @Bean
    public ICaptchaImageProvider initCaptchaImageProvider() {
        return new KaptchaImage();
    }

    @Bean
    public ISessionService initSessionService() {
        return new ServletSession();
    }
}
