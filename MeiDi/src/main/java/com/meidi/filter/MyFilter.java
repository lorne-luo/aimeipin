package com.meidi.filter;

import com.meidi.util.MdConstants;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * Created by luanpeng on 16/3/23.
 */
@WebFilter(filterName = "myFilter", urlPatterns = {"/user/*", "/class/*"})
public class MyFilter implements Filter, MdConstants {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {



        filterChain.doFilter(servletRequest, servletResponse);

    }


    @Override
    public void destroy() {

    }
}
