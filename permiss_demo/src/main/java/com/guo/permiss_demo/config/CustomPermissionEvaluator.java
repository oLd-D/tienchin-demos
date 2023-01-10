package com.guo.permiss_demo.config;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.io.Serializable;
import java.util.Collection;

// @Component
public class CustomPermissionEvaluator implements PermissionEvaluator {

    AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        System.out.println("authentication = " + authentication);
        System.out.println("targetDomainObject = " + targetDomainObject);
        System.out.println("permission = " + permission);
        // 获取当前用户所具备的所有角色
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (GrantedAuthority authority : authorities) {
            if (antPathMatcher.match(authority.getAuthority(), permission.toString())){
            // if(authority.getAuthority().equals(permission)) {
                // 说明当前登录的用户具备当前访问所需要的权限
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        return false;
    }
}
