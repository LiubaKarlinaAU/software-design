package ru.akirakozov.sd.refactoring.servlet;

import org.junit.Assert;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.sql.*;

import static org.mockito.Mockito.verify;

public class ServletTest {
    @Captor
    private ArgumentCaptor<String> contentTypeCaptor;
    @Captor
    private ArgumentCaptor<Integer> statusCaptor;
    @Mock
    protected HttpServletResponse response;

    private static final String DATABASE_URL = "jdbc:sqlite:test.db";
    private static final String DATABASE_FILENAME = "test.db";
    private static final String SQL_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS PRODUCT" +
            "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            " NAME           TEXT    NOT NULL, " +
            " PRICE          INT     NOT NULL)";

    protected static void prepareDatabase() throws SQLException {
        deleteDatabase();
        executeSQLQuerySilent(SQL_CREATE_TABLE);
    }

    protected static void deleteDatabase() {
        try {
            File file = new File(DATABASE_FILENAME);
            if (file.exists() && !file.delete()) {
                throw new Exception("Cannot delete file " + DATABASE_FILENAME);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected static void executeSQLQuerySilent(String sql) throws SQLException {
        try (Connection c = DriverManager.getConnection(DATABASE_URL)) {
            Statement stmt = c.createStatement();

            stmt.executeUpdate(sql);
            stmt.close();
        }
    }

    protected static String executeSQLQuery(String sql) throws SQLException, IOException {
        try (Connection c = DriverManager.getConnection(DATABASE_URL); StringWriter sw = new StringWriter()) {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String name = rs.getString("name");
                int price = rs.getInt("price");
                sw.write(name + " " + price + "\n");
            }
            rs.close();
            stmt.close();
            return sw.toString();
        }
    }

    protected void statusAndContentTypeVerification() {
        String expectedContentType = "text/html";
        Integer expectedStatus = 200;

        verify(response).setContentType(contentTypeCaptor.capture());
        verify(response).setStatus(statusCaptor.capture());

        Assert.assertEquals(1, contentTypeCaptor.getAllValues().size());
        Assert.assertEquals(expectedContentType, contentTypeCaptor.getAllValues().get(0));
        Assert.assertEquals(1, statusCaptor.getAllValues().size());
        Assert.assertEquals(expectedStatus, statusCaptor.getAllValues().get(0));
    }
}
