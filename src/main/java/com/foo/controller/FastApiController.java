package com.foo.controller;

import com.ajaxjs.data.crud.FastCRUD_Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fast_api")
public interface FastApiController extends FastCRUD_Controller {
}
