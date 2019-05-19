package com.epam.library.service;

import com.epam.library.dataBase.impl.UserDAOImpl;
import com.epam.library.entity.User;
import org.apache.commons.codec.digest.DigestUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

import static com.epam.library.validator.AuthorizationValidator.*;

public class LogInService implements Service {

    RequestDispatcher dispatcher;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException,
            SQLException {

        HttpSession session = request.getSession(true);

        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String md5Password = DigestUtils.md5Hex(password);
        UserDAOImpl userDAO = new UserDAOImpl();
        User user = userDAO.getUserByMailPassword(login, md5Password);

        boolean validatePasswordRegex = validatePasswordRegex(password);
        boolean validateLoginRegex = validateMailRegex(login);
        if(validateLoginRegex && validatePasswordRegex && user!=null){
            boolean isBlock = validateBlock(user);
            if(isBlock){
                String name = user.getName();
                String surname = user.getSurname();
                String role = user.getRole();
                int idUser = user.getId();
                session.setAttribute("idUser", idUser);
                session.setAttribute("role", role);
                session.setAttribute("name", name);
                session.setAttribute("surname", surname);
                session.setAttribute("login", login);
                session.setAttribute("password", password);
                ShowBookService showBookService = new ShowBookService();
                showBookService.execute(request, response);

            } else{
                 request.setAttribute("notCorrect", "Sorry your accaunt was block");
                 dispatcher = request.getRequestDispatcher("/authorization.jsp");
                 dispatcher.forward(request, response);
             }
        }
        else {
            request.setAttribute("notCorrect", "not correct login/password");
            dispatcher = request.getRequestDispatcher("/authorization.jsp");
            dispatcher.forward(request, response);
        }
    }
}
