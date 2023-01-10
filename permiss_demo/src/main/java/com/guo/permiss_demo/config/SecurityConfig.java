package com.guo.permiss_demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Bean
    UserDetailsService us() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("guo").password("{noop}123")
                // 给用户设置角色
                .roles("admin")
                // 给用户设置添加/删除用户的权限
                .authorities("system:user:add", "system:user:delete")
                // .authorities("system:user:*")
                .build());
        return manager;
    }

    @Bean
    CustomMethodSecurityExpressionHandler customMethodSecurityExpressionHandler() {
        return new CustomMethodSecurityExpressionHandler();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .permitAll();
        return http.build();
    }
}
