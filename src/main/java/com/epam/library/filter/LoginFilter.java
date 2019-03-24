package com.epam.library.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        HttpSession session = httpRequest.getSession();
        String role = (String) session.getAttribute("role");

        if(role!=null && (role.equalsIgnoreCase("reader") || role.equalsIgnoreCase("librarian"))){
            filterChain.doFilter(servletRequest, servletResponse);
        } else{
            ((HttpServletResponse) servletResponse).sendRedirect("/authorization.jsp");
        }

        System.out.println("filter is working");
    }

    @Override
    public void destroy() {

    }
}
