package ru.akirakozov.sd.refactoring.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author akirakozov
 */
public class AddProductServlet extends DatabaseServlet {
    @Override
    void doQuery(HttpServletRequest request, HttpServletResponse response, Statement stmt) throws IOException, SQLException {
        String name = request.getParameter("name");
        long price = Long.parseLong(request.getParameter("price"));
        String sql = makeInsertQuery(name, price);
        stmt.executeUpdate(sql);
        response.getWriter().println("OK");
    }

    private static String makeInsertQuery(String name, long price) {
        return "INSERT INTO PRODUCT " +
                "(NAME, PRICE) VALUES (\"" + name + "\"," + price + ")";
    }
}
