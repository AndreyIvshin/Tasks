package com.epam.docker;

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

    private static final PropertiesHolder propertiesHolder = PropertiesHolder.getInstance();

    public static final String H1 = "<h1>";
    public static final String SUCCESS_MESSAGE = "Connected";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Class.forName(propertiesHolder.getProperty(PropertiesHolder.DRIVER));
            try (Connection connection = DriverManager.getConnection(
                    propertiesHolder.getProperty(PropertiesHolder.URL),
                    propertiesHolder.getProperty(PropertiesHolder.USERNAME),
                    propertiesHolder.getProperty(PropertiesHolder.PASSWORD))) {
                    resp.getWriter().write(H1 + SUCCESS_MESSAGE + H1);
            }
        } catch (SQLException | IOException | ClassNotFoundException e) {
            resp.getWriter().write(H1 + e.getMessage() + H1);
            e.printStackTrace();
        }
    }
}
