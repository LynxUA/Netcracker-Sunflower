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
@WebFilter(filterName = "JSPFilter")
public class JSPFilter extends MainFilter {

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletResponse response = (HttpServletResponse) resp;
        response.sendRedirect("/webWeb/access_denied");
    }

}
