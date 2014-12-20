package com.sunflower.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * Created by denysburlakov on 19.12.14.
 */
@WebFilter(filterName = "Filter")
public abstract class MainFilter implements javax.servlet.Filter {
    public void destroy() {
    }

    public abstract void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException;

    public void init(FilterConfig config) throws ServletException {

    }

}
