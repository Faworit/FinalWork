package com.epam.library.service;

import com.epam.library.dataBase.UserDAO;
import com.epam.library.validator.RepeatPasswordValidator;
import org.apache.commons.codec.digest.DigestUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

public class ChangePasswordService implements Service {

    RequestDispatcher dispatcher;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException,
            SQLException {

        HttpSession session = request.getSession(true);

        String newPass = request.getParameter("newPass");
        String repeatPassword = request.getParameter("repeatPassword");
        if(!RepeatPasswordValidator.isEqualPasswords(newPass, repeatPassword)){
            request.setAttribute("notEqual", "passwords not equals");
            dispatcher = request.getRequestDispatcher("changePassword.jsp");
            dispatcher.forward(request, response);
        } else{
            String md5Password = DigestUtils.md5Hex(newPass);
            int idUser = (int) session.getAttribute("idUser");
            UserDAO userDAO = new UserDAO();
            userDAO.changePassword(md5Password, idUser);

            request.setAttribute("information", "password changed success");
            dispatcher = request.getRequestDispatcher("information.jsp");
            dispatcher.forward(request, response);
        }
    }
}
