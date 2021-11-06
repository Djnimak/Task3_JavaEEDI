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

public class CreateUserServlet extends HttpServlet {



    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        ApplicationContext beanFactory = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
        User user = beanFactory.getBean(Constants.USER, User.class);
        UserService service = (UserService) beanFactory.getBean(Constants.USER_SERVICE);

        user.setFirstName(request.getParameter(Constants.FIRST_NAME));
        user.setLastName(request.getParameter(Constants.LAST_NAME));
        user.setEmail(request.getParameter(Constants.EMAIL));
        user.setPassword(request.getParameter(Constants.PASSWORD));
        user.setGender(request.getParameter(Constants.GENDER));
        try {
            user.setAge(Integer.parseInt(request.getParameter(Constants.AGE)));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        RequestDispatcher requestDispatcher;
        try {
            int result = service.createUser(user);
            requestDispatcher = request.getRequestDispatcher(Constants.INDEX_JSP);
            if (result > 0) {
                request.setAttribute(Constants.USER_CREATED, Constants.USER_CREATED_MESSAGE);
                requestDispatcher.forward(request, response);
            } else {
                request.setAttribute(Constants.USER_NOT_CREATED, Constants.USER_NOT_CREATED_MESSAGE);
                requestDispatcher = request.getRequestDispatcher(Constants.CREATE_JSP);
                requestDispatcher.include(request, response);
            }
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }

}
