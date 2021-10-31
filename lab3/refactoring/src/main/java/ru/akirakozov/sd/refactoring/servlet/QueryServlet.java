package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.servlet.utils.HTMLWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author akirakozov
 */
public class QueryServlet extends DatabaseServlet {
    private static final String MAX = "max";
    private static final String MIN = "min";
    private static final String SUM = "sum";
    private static final String COUNT = "count";
    private static final Map<String, String> commandQuery = new HashMap<String, String>() {{
        put(MAX, "SELECT * FROM PRODUCT ORDER BY PRICE DESC LIMIT 1");
        put(MIN, "SELECT * FROM PRODUCT ORDER BY PRICE LIMIT 1");
        put(SUM, "SELECT SUM(price) FROM PRODUCT");
        put(COUNT, "SELECT COUNT(*) FROM PRODUCT");
    }};

    private static final Map<String, String> commandFirstLine = new HashMap<String, String>() {{
        put(MAX, "<h1>Product with max price: </h1>");
        put(MIN, "<h1>Product with min price: </h1>");
        put(SUM, "Summary price: ");
        put(COUNT, "Number of products: ");
    }};

    @Override
    void doQuery(HttpServletRequest request, HttpServletResponse response, Statement stmt) throws IOException, SQLException {
        String command = request.getParameter("command");
        if (commandQuery.containsKey(command)) {
            String query = commandQuery.get(command);
            String firstLine = commandFirstLine.get(command);
            ResultSet rs = stmt.executeQuery(query);
            HTMLWriter writer = new HTMLWriter(response.getWriter(), rs);
            writer.writeCommand(firstLine, isOneNumberAnswer(command));
            rs.close();
        } else {
            response.getWriter().println("Unknown command: " + command);
        }
    }

    private static boolean isOneNumberAnswer(String command) {
        return SUM.equals(command) || COUNT.equals(command);
    }
}
