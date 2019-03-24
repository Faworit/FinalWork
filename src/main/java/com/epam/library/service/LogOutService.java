package com.epam.library.service;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;

public class LogOutService implements Service {

    RequestDispatcher dispatcher;
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException {
        request.getSession().invalidate();
        response.sendRedirect("/authorization.jsp");
    }
}
