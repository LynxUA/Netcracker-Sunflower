package com.sunflower.filters;

import com.sunflower.constants.UserGroups;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by denysburlakov on 19.12.14.
 */
@WebFilter(filterName = "AdminFilter")
public class AdminFilter extends  MainFilter{
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        Integer status = (Integer)(request.getSession().getAttribute("status"));
        if(status == null){
            response.sendRedirect("/webWeb/login");
        }else if(!(status== UserGroups.ADMIN)) {
            response.sendRedirect("/webWeb/access_denied");
        }else {
            chain.doFilter(request, response);
        }
    }

}
