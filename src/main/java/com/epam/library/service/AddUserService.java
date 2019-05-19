package com.epam.library.service;

import com.epam.library.dataBase.UserDAO;
import com.epam.library.entity.User;
import com.epam.library.validator.AuthorizationValidator;
import com.epam.library.validator.RepeatPasswordValidator;
import org.apache.commons.codec.digest.DigestUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class AddUserService implements Service {

    RequestDispatcher dispatcher;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException,
            ParseException, SQLException {

        String password = request.getParameter("password");
        String mail = request.getParameter("mail");
        UserDAO userDAO = new UserDAO();
        boolean isCorrectMail = AuthorizationValidator.validateMailRegex(mail);
        boolean isUser = userDAO.checkUser(mail);
        String repeatPassword = request.getParameter("repeatPassword");
        if(!RepeatPasswordValidator.isEqualPasswords(password, repeatPassword)){
            request.setAttribute("notEqual", "passwords not equals");
            dispatcher = request.getRequestDispatcher("setUser.jsp");
            dispatcher.forward(request, response);
        } else if(!isCorrectMail){
            request.setAttribute("notCorrectMail", "not correct format of mail");
            dispatcher = request.getRequestDispatcher("setUser.jsp");
            dispatcher.forward(request, response);
        } else if(isUser){
            request.setAttribute("loginExists", "user with this login is exists");
            dispatcher = request.getRequestDispatcher("setUser.jsp");
            dispatcher.forward(request, response);
        } else{
            User user = createUser(request, password);
            userDAO.create(user);
            request.setAttribute("information", "new User created successfully");
            dispatcher = request.getRequestDispatcher("information.jsp");
            dispatcher.forward(request, response);
        }
    }

    private User createUser(HttpServletRequest request, String password) throws ParseException {
        User user = new User();
        user.setName(request.getParameter("name"));
        user.setSurname(request.getParameter("surname"));
        String md5Password = DigestUtils.md5Hex(password);
        user.setPassword(md5Password);
        user.setTelephone(request.getParameter("phone"));
        user.setBlock(request.getParameter("block"));
        user.setRole(request.getParameter("role"));
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date birthday = format.parse(request.getParameter("birthday"));
        user.setBirthDay(birthday);

        return user;
    }
}
