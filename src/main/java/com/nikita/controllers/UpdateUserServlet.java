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

public class UpdateUserServlet extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        ApplicationContext beanFactory = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
        User user = beanFactory.getBean(Constants.USER, User.class);
        UserService service = (UserService) beanFactory.getBean(Constants.USER_SERVICE);

        user.setEmail(request.getParameter(Constants.EMAIL));
        user.setPassword(request.getParameter(Constants.PASSWORD));
        String newEmail = request.getParameter(Constants.NEW_EMAIL);
        String newPassword = request.getParameter(Constants.NEW_PASSWORD);
        RequestDispatcher requestDispatcher;
        try {
            int result = service.updateUser(user, newEmail, newPassword);
            requestDispatcher = request.getRequestDispatcher(Constants.INDEX_JSP);
            if (result > 0) {
                request.setAttribute(Constants.USER_UPDATED, Constants.USER_UPDATED_MESSAGE);
                requestDispatcher.forward(request, response);
            } else {
                request.setAttribute(Constants.USER_NOT_UPDATED, Constants.USER_NOT_UPDATED_MESSAGE);
                requestDispatcher = request.getRequestDispatcher(Constants.UPDATE_JSP);
                requestDispatcher.include(request, response);
            }
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }


}
