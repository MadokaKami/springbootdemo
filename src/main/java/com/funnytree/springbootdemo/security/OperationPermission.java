package com.funnytree.springbootdemo.security;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

/**
 * @Description 业务操作权限
 * @ClassName OperationPermission
 * @author 李英夫
 * @since 2018/10/27 13:39
 * @version V1.0.0
 * @Copyright (c) All Rights Reserved, 2018.
 */
@Component
public class OperationPermission {

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
        Object principal = authentication.getPrincipal();
        boolean hasPermission = false;
        if(principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            // 读取用户所拥有的权限url
            Set<String> urls = new HashSet<>();
            urls.add("/user");
            for (String url : urls) {
                if(antPathMatcher.match(url, request.getRequestURI())) {
                    hasPermission = true;
                    break;
                }
            }

        }
        return hasPermission;
    }

}
