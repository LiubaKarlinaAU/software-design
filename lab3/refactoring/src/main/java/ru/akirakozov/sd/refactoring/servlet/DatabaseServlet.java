package ru.akirakozov.sd.refactoring.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class DatabaseServlet extends HttpServlet {
    private static final String DATABASE_URL = "jdbc:sqlite:test.db";
    private static final String CONTENT_TYPE = "text/html";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            try (Connection c = DriverManager.getConnection(DATABASE_URL)) {
                Statement stmt = c.createStatement();
                doQuery(request, response, stmt);
                stmt.close();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        response.setContentType(CONTENT_TYPE);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    abstract void doQuery(HttpServletRequest request, HttpServletResponse response, Statement stmt) throws IOException, SQLException;
}
