package com.sunflower.web;

import com.sunflower.ejb.usergroup.LocalUserGroup;
import com.sunflower.ejb.usergroup.LocalUserGroupHome;
import org.apache.log4j.Logger;

import javax.ejb.CreateException;
import javax.ejb.FinderException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Den on 02.12.2014.
 */
public class UserGroupServlet extends HttpServlet {
    private static Logger logger = Logger.getLogger(UserGroupServlet.class);
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            InitialContext ic = new InitialContext();

            LocalUserGroupHome groupHome = (LocalUserGroupHome) ic.lookup("java:comp/env/ejb/UserGroup");
            LocalUserGroup userGroup = null;
            try {
                userGroup = groupHome.findByPrimaryKey(1);

            } catch (FinderException e) {
                logger.error(e.getMessage(), e);
            }

            PrintWriter pw = response.getWriter();
            pw.println(userGroup.getColumnName());

        } catch (NamingException e) {
            logger.error(e.getMessage(), e);
        }
    }
}
