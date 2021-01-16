package com.daniel.controller;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.daniel.dao.UserInfoDao;
import com.daniel.model.UserInfo;

public class UserInfoController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static String INSERT_OR_EDIT = "/user.jsp";
    private static String LIST_USER = "/listUser.jsp";
    private UserInfoDao dao;

    public UserInfoController() {
        super();
        dao = new UserInfoDao();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String forward="";
        String action = request.getParameter("action");

        if (action.equalsIgnoreCase("delete")){
            int id = Integer.parseInt(request.getParameter("id"));
            dao.deleteUser(id);
            forward = LIST_USER;
            request.setAttribute("userinfo", dao.getAllUsers());    
        } else if (action.equalsIgnoreCase("edit")){
            forward = INSERT_OR_EDIT;
            int id = Integer.parseInt(request.getParameter("id"));
            UserInfo userinfo = dao.getUserById(id);
            request.setAttribute("userinfo", userinfo);
        } else if (action.equalsIgnoreCase("listUser")){
            forward = LIST_USER;
            request.setAttribute("userinfo", dao.getAllUsers());
        } else {
            forward = INSERT_OR_EDIT;
        }

        RequestDispatcher view = request.getRequestDispatcher(forward);
        view.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserInfo userinfo = new UserInfo();
        String id = request.getParameter("id");

		userinfo.setName(request.getParameter("name"));
		userinfo.setAddress(request.getParameter("address"));
		userinfo.setAge(Integer.parseInt(request.getParameter("age")));
		userinfo.setEdu(Integer.parseInt(request.getParameter("edu")));
		userinfo.setSex(Integer.parseInt(request.getParameter("sex")));
        
        if(id == null || id.isEmpty())
        {
            dao.addUserInfo(userinfo);
        }
        else
        {
        	userinfo.setId(Integer.parseInt(id));
            dao.updateUser(userinfo);
        }
        RequestDispatcher view = request.getRequestDispatcher(LIST_USER);
        request.setAttribute("users", dao.getAllUsers());
        view.forward(request, response);
    }
}