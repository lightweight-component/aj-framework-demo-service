package com.foo;

import com.ajaxjs.framework.embeded_tomcat.BaseWebMvcConfigure;
import com.ajaxjs.framework.embeded_tomcat.EmbeddedTomcatStarter;
import com.ajaxjs.framework.embeded_tomcat.TomcatConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@ComponentScan({"com.foo"})
public class FooApplication extends BaseWebMvcConfigure {
    public static void main(String[] args) {
        TomcatConfig cfg = new TomcatConfig();
        cfg.setEnableJsp(true);
        cfg.setPort(8080);
        cfg.setContextPath("/foo");

        new EmbeddedTomcatStarter(cfg, new Class[]{FooApplication.class}).start();
    }
}