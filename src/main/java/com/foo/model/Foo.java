package com.foo.model;

import com.ajaxjs.framework.IBaseModel;
import lombok.Data;

@Data
public class Foo implements IBaseModel {
    private Long id;

    private String name;
}
