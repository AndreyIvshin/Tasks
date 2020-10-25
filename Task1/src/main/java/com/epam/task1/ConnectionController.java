package com.epam.task1;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@WebServlet("/connection")
public class ConnectionController extends HttpServlet {

    private void process(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@192.168.99.100:1521:oracle", "root", "1234");
            resp.getWriter().write("<h1>Connected</h1>");
        } catch (SQLException | IOException | ClassNotFoundException e) {
            resp.getWriter().write("<h1>" + e.getMessage() + "</h1>");
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }
}
