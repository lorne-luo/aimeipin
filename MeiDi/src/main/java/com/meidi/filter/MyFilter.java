package com.meidi.filter;

import com.meidi.util.MdConstants;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;

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
//        HttpSession session = ((HttpServletRequest)servletRequest).getSession();
//        servletResponse.setCharacterEncoding("utf-8");
//
//        if(session.getAttribute("user")==null){
//            PrintWriter out = servletResponse.getWriter();
//            out.print("<script language=javascript>alert('you do not login!');window.location.href='../index.jsp';</script>");
//        }else{
//            filterChain.doFilter(servletRequest, servletResponse);
//        }

        filterChain.doFilter(servletRequest, servletResponse);
    }


    @Override
    public void destroy() {

    }
}
