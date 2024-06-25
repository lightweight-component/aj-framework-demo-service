package com.foo;

import com.ajaxjs.framework.embeded_tomcat.EmbeddedTomcatStarter;
import com.ajaxjs.framework.embeded_tomcat.TomcatConfig;
import com.ajaxjs.framework.spring.BaseWebMvcConfigure;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@ComponentScan({"com.foo"})
public class FooApplication extends BaseWebMvcConfigure {
    public static void main(String[] args) {
        EmbeddedTomcatStarter.start(FooApplication.class);
    }
}