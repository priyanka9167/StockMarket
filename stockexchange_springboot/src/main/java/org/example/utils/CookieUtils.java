package org.example.utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.model.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;

@Service
public class CookieUtils {

    @Autowired
    HttpServletRequest httpServletRequest;

    @Autowired
    HttpServletResponse httpServletResponse;

    @Autowired
    SecurityProperties restSecProps;

    public Cookie getCookie(String name){
        return WebUtils.getCookie(httpServletRequest, name);
    }

    public  void setCookie(String name, String value, int expiryInMinutes){
        int expiryInSeconds = expiryInMinutes * 60 * 60;
        Cookie cookie = new Cookie(name,value);
        cookie.setSecure(restSecProps.getCookieProps().isSecure());
        cookie.setPath(restSecProps.getCookieProps().getPath());
        cookie.setDomain(restSecProps.getCookieProps().getDomain());
        cookie.setMaxAge(expiryInSeconds);
        httpServletResponse.addCookie(cookie);
    }

    public void setSecureCookie(String name, String value, int expiryInMinutes){
        int expiresInSeconds = expiryInMinutes * 60 * 60;
        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(restSecProps.getCookieProps().isHttpOnly());
        cookie.setSecure(restSecProps.getCookieProps().isSecure());
        cookie.setPath(restSecProps.getCookieProps().getPath());
        cookie.setDomain(restSecProps.getCookieProps().getDomain());
        cookie.setMaxAge(expiresInSeconds);
        httpServletResponse.addCookie(cookie);
    }
    public void setSecureCookie(String name, String value){
        int expiryInMinutes = restSecProps.getCookieProps().getMaxAgeInMinutes();
        setSecureCookie(name,value,expiryInMinutes);
    }

    public  void deleteSecureCookie(String name){
        int expiryInSeconds = 0;
        Cookie cookie = new Cookie(name,null);
        cookie.setHttpOnly(restSecProps.getCookieProps().isHttpOnly());
        cookie.setSecure(restSecProps.getCookieProps().isSecure());
        cookie.setPath(restSecProps.getCookieProps().getPath());
        cookie.setDomain(restSecProps.getCookieProps().getDomain());
        cookie.setMaxAge(expiryInSeconds);
        httpServletResponse.addCookie(cookie);
    }

    public  void deleteCookie(String name){
        int expiryInSeconds = 0;
        Cookie cookie = new Cookie(name,null);
        cookie.setPath(restSecProps.getCookieProps().getPath());
        cookie.setDomain(restSecProps.getCookieProps().getDomain());
        cookie.setMaxAge(expiryInSeconds);
        httpServletResponse.addCookie(cookie);
    }
}
