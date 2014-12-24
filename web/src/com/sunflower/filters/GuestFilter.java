package com.sunflower.filters;

import com.sunflower.constants.UserGroups;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by denysburlakov on 24.12.14.
 */
@WebFilter(filterName = "GuestFilter")
public class GuestFilter extends MainFilter {

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        Integer status = (Integer)(request.getSession().getAttribute("status"));
        if(status == null){
            chain.doFilter(request, response);
        }else {
            response.sendRedirect("/webWeb/access_denied");
        }
    }

}
