package com.funnytree.springbootdemo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
//@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * user-detail服务配置
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().passwordEncoder(getPasswordEncoder())
                .withUser("liyf").password(getPasswordEncoder().encode("liyf")).roles("role");
        //super.configure(auth);
    }

    /**
     * 拦截器保护请求
     * @param http http请求web安全配置类
     * @throws Exception
     * @see org.springframework.security.web.access.expression.WebSecurityExpressionRoot access表达式看这里
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/").permitAll()
                .and()
                .authorizeRequests().antMatchers("/**")
                    .access("@operationPermission.hasPermission(request, authentication)");
        http.cors().disable();
        http.csrf().disable();
        super.configure(http);
    }

    /**
     * 拦截器链配置
     * @param web 拦截器链配置类
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().mvcMatchers("/**/*.js", "/**/*.css", "/**/*.woff", "/**/*.woff2", "/**/*.jpg", "/**/*.png", "/**/*.ico");
    }

    private PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder(15);
    }
}
