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

public class ChengePasswordService implements Service {

    RequestDispatcher dispatcher;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        int idUser;
        String newPass = request.getParameter("newPass");
        String repeatPassword = request.getParameter("repeatPassword");
        String md5Password = DigestUtils.md5Hex(newPass);
        UserDAO userDAO = new UserDAO();
        HttpSession session = request.getSession(true);
        idUser = (int) session.getAttribute("idUser");
        if(!RepeatPasswordValidator.isEqualPasswords(newPass, repeatPassword)){
            request.setAttribute("notEqual", "passwords not equals");
            dispatcher = request.getRequestDispatcher("setUser.jsp");
            dispatcher.forward(request, response);
        } else{
            userDAO.chengePassword(md5Password, idUser);
            response.sendRedirect("jsp/user.jsp");
        }
    }
}
