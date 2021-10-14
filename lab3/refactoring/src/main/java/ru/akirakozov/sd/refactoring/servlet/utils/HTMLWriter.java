package ru.akirakozov.sd.refactoring.servlet.utils;

import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HTMLWriter {
    private final PrintWriter writer;
    private final ResultSet rs;
    private static final String HTML_START = "<html><body>";
    private static final String HTML_END = "</body></html>";

    public HTMLWriter(PrintWriter writer, ResultSet rs) {
        this.writer = writer;
        this.rs = rs;
    }

    public void writeCommand(String firstLine, boolean isOneNumberAnswer) throws SQLException {
        writer.println(HTML_START);
        writer.println(firstLine);

        if (isOneNumberAnswer) {
            writeOneNumber();
        } else {
            writeAllRows();
        }
        writer.println(HTML_END);
    }

    public void writeGet() throws SQLException {
        writer.println(HTML_START);

        writeAllRows();

        writer.println(HTML_END);
    }

    private void writeAllRows() throws SQLException {
        while (rs.next()) {
            String name = rs.getString("name");
            int price = rs.getInt("price");
            writer.println(name + "\t" + price + "</br>");
        }
    }

    private void writeOneNumber() throws SQLException {
        if (rs.next()) {
            writer.println(rs.getInt(1));
        }
    }
}
