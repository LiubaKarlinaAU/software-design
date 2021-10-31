package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.servlet.utils.HTMLWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

/**
 * @author akirakozov
 */
public class GetProductsServlet extends DatabaseServlet {
    private static final String GET_QUERY = "SELECT * FROM PRODUCT";

    @Override
    void doQuery(HttpServletRequest request, HttpServletResponse response, Statement stmt) throws IOException, SQLException {
        ResultSet rs = stmt.executeQuery(GET_QUERY);

        HTMLWriter writer = new HTMLWriter(response.getWriter(), rs);
        writer.writeGet();

        rs.close();
    }
}
