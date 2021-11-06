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
import java.util.List;

public class ShowUserServlet extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ApplicationContext beanFactory = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
        UserService service = (UserService) beanFactory.getBean(Constants.USER_SERVICE);

        String email = request.getParameter(Constants.EMAIL);
        String command = request.getParameter(Constants.SHOW_ALL_USERS);
        RequestDispatcher requestDispatcher;
        try {
            List<User> list;
            if (command == null) {
                list = service.findUserByEmail(email);
            } else {
                list = service.findAllUsers();
            }
            if (list.isEmpty()) {
                request.setAttribute(Constants.NO_SUCH_USER, Constants.NO_SUCH_USER_MESSAGE);
                requestDispatcher = request.getRequestDispatcher(Constants.SHOW_JSP);
                requestDispatcher.include(request, response);
            } else {
                request.setAttribute(Constants.USER, list);
                requestDispatcher = request.getRequestDispatcher(Constants.USER_BY_EMAIL_JSP);
                requestDispatcher.forward(request, response);
            }
        } catch (ServletException | IOException throwables) {
            throwables.printStackTrace();
        }
    }
}
