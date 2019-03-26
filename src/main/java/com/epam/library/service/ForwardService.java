package com.epam.library.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ForwardService implements Service {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String direction = request.getParameter("direction");
        System.out.println(direction);
        response.sendRedirect(direction);
    }
}
