package com.foo.controller;

import com.ajaxjs.framework.filter.dbconnection.IgnoreDataBaseConnect;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/sec")
public interface SecurityController {
    /**
     *
     * @param req
     * @param resp
     * @param token Random Code
     */
    @GetMapping("/captcha/{token}")
    @IgnoreDataBaseConnect
    void captchaImage(HttpServletRequest req, HttpServletResponse resp, @PathVariable String token);
}
