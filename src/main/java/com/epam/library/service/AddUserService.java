package com.epam.library.service;

import com.epam.library.dataBase.CheckUserDAO;
import com.epam.library.dataBase.CreateUserDAO;
import com.epam.library.validator.AuthorizationValidator;
import com.epam.library.validator.RepeatPasswordValidator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class AddUserService implements Service {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException {
        RequestDispatcher dispatcher;
        CreateUserDAO createUserDAO = new CreateUserDAO();
        CheckUserDAO checkUserDAO = new CheckUserDAO();
        String password = request.getParameter("password");
        String repeatPassword = request.getParameter("repeatPassword");
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String mail = request.getParameter("mail");
        String telephone = request.getParameter("phone");
        String block = request.getParameter("block");
        String role = request.getParameter("role");
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date birthday = format.parse(request.getParameter("birthday"));
        boolean isCorrectMail;
        boolean isUser;
        isCorrectMail = AuthorizationValidator.validateMailRegex(mail);
        if(!RepeatPasswordValidator.isEqualPasswords(password, repeatPassword)){
            request.setAttribute("notEqual", "passwords not equals");
        }
        if(!isCorrectMail){
            request.setAttribute("notCorrectMail", "not correct format of mail");
            dispatcher = request.getRequestDispatcher("jsp/setUser.jsp");
            dispatcher.forward(request, response);
        }
        isUser = checkUserDAO.checkUser(mail);
        if(isUser){
            request.setAttribute("loginExists", "user with this login or ID exists");
            dispatcher = request.getRequestDispatcher("jsp/setUser.jsp");
            dispatcher.forward(request, response);
        } else{
            createUserDAO.setUser(password, name, surname,  mail, telephone, birthday, block, role);
            request.setAttribute("information", "new User created successfully");
            dispatcher = request.getRequestDispatcher("jsp/information.jsp");
            dispatcher.forward(request, response);
        }
    }
}
