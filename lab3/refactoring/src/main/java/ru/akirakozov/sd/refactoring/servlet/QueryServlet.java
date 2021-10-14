package ru.akirakozov.sd.refactoring.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author akirakozov
 */
public class QueryServlet extends HttpServlet {
    private static final String MAX = "max";
    private static final String MIN = "min";
    private static final String SUM = "sum";
    private static final String COUNT = "count";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String command = request.getParameter("command");

        switch (command) {
            case MAX:
                doCommand(response, "SELECT * FROM PRODUCT ORDER BY PRICE DESC LIMIT 1", "<h1>Product with max price: </h1>", false);
                break;
            case MIN:
                doCommand(response, "SELECT * FROM PRODUCT ORDER BY PRICE LIMIT 1", "<h1>Product with min price: </h1>", false);
                break;
            case SUM:
                doCommand(response, "SELECT SUM(price) FROM PRODUCT", "Summary price: ", true);
                break;
            case COUNT:
                doCommand(response, "SELECT COUNT(*) FROM PRODUCT", "Number of products: ", true);
                break;
            default:
                response.getWriter().println("Unknown command: " + command);
                break;
        }

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    private void doCommand(HttpServletResponse response, String query, String firstLine, boolean isSingleLineAnswer) {
        try {
            try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
                Statement stmt = c.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                response.getWriter().println("<html><body>");
                response.getWriter().println(firstLine);

                if (isSingleLineAnswer) {
                    if (rs.next()) {
                        response.getWriter().println(rs.getInt(1));
                    }
                } else {
                    while (rs.next()) {
                        String name = rs.getString("name");
                        int price = rs.getInt("price");
                        response.getWriter().println(name + "\t" + price + "</br>");
                    }
                }
                response.getWriter().println("</body></html>");

                rs.close();
                stmt.close();
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
