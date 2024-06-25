package com.foo.service;

import com.ajaxjs.framework.spring.filter.dbconnection.IgnoreDataBaseConnect;
import com.foo.controller.FooController;
import com.foo.model.Foo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FooService implements FooController {
    @Override
    @IgnoreDataBaseConnect
    public Foo getFoo() { // do your business code
        Foo foo = new Foo();
        foo.setId(888L);
        foo.setName("Jack");

        return foo;
    }
}
