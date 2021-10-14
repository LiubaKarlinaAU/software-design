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
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String command = request.getParameter("command");

        if ("max".equals(command)) {
            doCommand(response, "SELECT * FROM PRODUCT ORDER BY PRICE DESC LIMIT 1", "<h1>Product with max price: </h1>", false);
        } else if ("min".equals(command)) {
            doCommand(response, "SELECT * FROM PRODUCT ORDER BY PRICE LIMIT 1", "<h1>Product with min price: </h1>", false);
        } else if ("sum".equals(command)) {
            doCommand(response, "SELECT SUM(price) FROM PRODUCT", "Summary price: ",true);
        } else if ("count".equals(command)) {
            doCommand(response, "SELECT COUNT(*) FROM PRODUCT", "Number of products: ", true);
        } else {
            response.getWriter().println("Unknown command: " + command);
        }

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    private void doCommand(HttpServletResponse response, String query, String firstLine, boolean isSingleLineAnswer){
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
                        String  name = rs.getString("name");
                        int price  = rs.getInt("price");
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
