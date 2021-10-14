package ru.akirakozov.sd.refactoring.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

/**
 * @author akirakozov
 */
public class QueryServlet extends HttpServlet {
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String command = request.getParameter("command");
        if (commandQuery.containsKey(command)) {
            doCommand(response, command);
        } else {
            response.getWriter().println("Unknown command: " + command);
        }
    }

    private void doCommand(HttpServletResponse response, String command) {
        String query = commandQuery.get(command);
        String firstLine = commandFirstLine.get(command);
        try {
            try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
                Statement stmt = c.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                PrintWriter writer = response.getWriter();
                writer.println("<html><body>");
                writer.println(firstLine);

                if (isOneNumberAnswer(command)) {
                    if (rs.next()) {
                        writer.println(rs.getInt(1));
                    }
                } else {
                    while (rs.next()) {
                        String name = rs.getString("name");
                        int price = rs.getInt("price");
                        writer.println(name + "\t" + price + "</br>");
                    }
                }
                writer.println("</body></html>");

                rs.close();
                stmt.close();
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    private static boolean isOneNumberAnswer(String command) {
        return SUM.equals(command) || COUNT.equals(command);
    }
}
