package com.foo.service;

import com.ajaxjs.security.captcha.image.ICaptchaImageProvider;
import com.ajaxjs.security.session.ISessionService;
import com.ajaxjs.util.StrUtil;
import com.foo.controller.SecurityController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
public class SecurityService implements SecurityController {
    @Autowired
    ICaptchaImageProvider provider;

    @Autowired
    ISessionService sessionService;

    @Override
    public void captchaImage(HttpServletRequest req, HttpServletResponse resp, String token) {
        resp.setDateHeader("Expires", 0);
        // Set standard HTTP/1.1 no-cache headers.
        resp.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        // 0Set IE extended HTTP/1.1 no-cache headers (use addHeader).
        resp.addHeader("Cache-Control", "post-check=0, pre-check=0");
        // Set standard HTTP/1.0 no-cache header.
        resp.setHeader("Pragma", "no-cache");

        // return a jpeg
        resp.setContentType("image/jpeg");

        String capText = StrUtil.getRandomString(6);
        // store the text in the session
        sessionService.set("CAP_" + token, capText);

        try {
            ImageIO.write(provider.getRenderedImage(200, 50, capText), "jpg", resp.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
