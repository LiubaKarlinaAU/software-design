package ru.akirakozov.sd.refactoring.servlet.utils;

import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HTMLWriter {
    PrintWriter writer;
    ResultSet rs;

    public HTMLWriter(PrintWriter writer, ResultSet rs) {
        this.writer = writer;
        this.rs = rs;
    }

    public void writeCommand(String firstLine, boolean isOneNumberAnswer) throws SQLException {
        writer.println("<html><body>");
        writer.println(firstLine);

        if (isOneNumberAnswer) {
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
    }
}
