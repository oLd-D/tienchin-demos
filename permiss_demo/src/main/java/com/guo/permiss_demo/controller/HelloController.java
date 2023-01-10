package com.guo.permiss_demo.controller;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public void hello() {
        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        for (GrantedAuthority authority : authorities) {
            System.out.println("authority = " + authority);
            // authority = system:user:add
            // authority = system:user:delete
        }
    }
}
