package com.epam.library.service;

import com.epam.library.dataBase.UserDAO;
import com.epam.library.entity.User;
import org.apache.commons.codec.digest.DigestUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.epam.library.validator.AuthorizationValidator.*;

public class LogInService implements Service {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher;
        int idUser;
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String name;
        String surname;
        String role;
        boolean validateLoginRegex;
        boolean validatePasswordRegex;
        boolean isBlock;
        User user;
        UserDAO userDAO = new UserDAO();
        validateLoginRegex = validateMailRegex(login);
        validatePasswordRegex = validatePasswordRegex(password);
        String md5Password = DigestUtils.md5Hex(password);
        user = userDAO.getUserByMailPassword(login, md5Password);
        if(validateLoginRegex && validatePasswordRegex && user!=null){
            isBlock = validateBlock(user);
            if(isBlock){
                HttpSession session = request.getSession(true);
                name = user.getName();
                surname = user.getSurname();
                role = user.getRole();
                idUser = user.getIDUser();
                session.setAttribute("idUser", idUser);
                session.setAttribute("role", role);
                session.setAttribute("name", name);
                session.setAttribute("surname", surname);
                session.setAttribute("login", login);
                session.setAttribute("password", password);
                ShowBookService showBookService = new ShowBookService();
                showBookService.execute(request, response);
                dispatcher = request.getRequestDispatcher("jsp/user.jsp");
                dispatcher.forward(request, response);
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
