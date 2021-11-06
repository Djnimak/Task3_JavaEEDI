package com.nikita.controllers;

import com.nikita.constants.Constants;
import com.nikita.model.entity.User;
import com.nikita.model.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteUserServlet extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ApplicationContext beanFactory = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
        User user = beanFactory.getBean(Constants.USER, User.class);
        UserService service = (UserService) beanFactory.getBean(Constants.USER_SERVICE);

        user.setEmail(request.getParameter(Constants.EMAIL));
        user.setPassword(request.getParameter(Constants.PASSWORD));
        String adminPassword = request.getParameter(Constants.ADMIN_PASSWORD);
        RequestDispatcher requestDispatcher;
        if (adminPassword == null) {
            try {
                int result = service.deleteUser(user);
                requestDispatcher = request.getRequestDispatcher(Constants.INDEX_JSP);
                if (result > 0) {
                    request.setAttribute(Constants.USER_DELETED, Constants.USER_DELETED_MESSAGE);
                    requestDispatcher.forward(request, response);
                } else {
                    request.setAttribute(Constants.USER_NOT_DELETED, Constants.USER_NOT_DELETED_MESSAGE);
                    requestDispatcher = request.getRequestDispatcher(Constants.DELETE_JSP);
                    requestDispatcher.include(request, response);
                }
            } catch (ServletException | IOException e) {
                e.printStackTrace();
            }
        } else {
            if (adminPassword.equals(Constants.ADMIN_PASSWORD_VALUE)) {
                int result = service.deleteAllUsers();
                requestDispatcher = request.getRequestDispatcher(Constants.INDEX_JSP);
                if (result > 0) {
                    request.setAttribute(Constants.ALL_USERS_DELETED, Constants.ALL_USERS_DELETED_MESSAGE);
                    requestDispatcher.forward(request, response);
                } else {
                    request.setAttribute(Constants.USERS_NOT_DELETED, Constants.USERS_NOT_DELETED_MESSAGE);
                    requestDispatcher = request.getRequestDispatcher(Constants.DELETE_JSP);
                    requestDispatcher.include(request, response);
                }
            } else {
                request.setAttribute(Constants.USERS_NOT_DELETED, Constants.USERS_NOT_DELETED_MESSAGE);
                requestDispatcher = request.getRequestDispatcher(Constants.DELETE_JSP);
                requestDispatcher.include(request, response);
            }
        }
    }

}
