package com.epam.library.controller;

import com.epam.library.service.Service;
import com.epam.library.service.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.TimeZone;

public class LibraryController extends HttpServlet {

    private static long serialVersionUID = 1L;
    private final Logger log = Logger.getLogger(this.getClass().getName());

    public LibraryController(){
        super();
    }

    @Override
    public void init() throws ServletException {
        super.init();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        String s = request.getRequestURI();
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        Service service = serviceFactory.getService(s);
        try {
            service.execute(request, response);
        } catch (ParseException e) {
            log.error(e);
        } catch (SQLException e) {
            log.error(e);
        }
    }
}
