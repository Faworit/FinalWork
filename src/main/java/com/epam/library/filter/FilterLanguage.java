package com.epam.library.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class FilterLanguage implements Filter {

    public static final String ENGLISH = "ENG";
    public static final String RUSSIAN = "RU";
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpSession session = httpRequest.getSession();

        String language = servletRequest.getParameter("language");
        if(servletRequest.getParameter("language")!=null){
            if(language.equalsIgnoreCase(ENGLISH)){
                session.setAttribute("language", ENGLISH);
            }
            else{
                session.setAttribute("language", RUSSIAN);
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy(){}
}
