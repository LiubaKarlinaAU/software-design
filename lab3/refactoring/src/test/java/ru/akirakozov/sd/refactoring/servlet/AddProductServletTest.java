package ru.akirakozov.sd.refactoring.servlet;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.*;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AddProductServletTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Captor
    private ArgumentCaptor<String> contentTypeCaptor;
    @Captor
    private ArgumentCaptor<Integer> statusCaptor;

    private AddProductServlet servlet;
    private static final String DATABASE_URL = "jdbc:sqlite:test.db";
    private static final String DATABASE_FILENAME = "test.db";
    private static final String SQL_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS PRODUCT" +
            "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            " NAME           TEXT    NOT NULL, " +
            " PRICE          INT     NOT NULL)";

    @Before
    public void setUp() throws Exception {
        deleteDataBase();
        executeSQLQuerySilent(SQL_CREATE_TABLE);
        MockitoAnnotations.initMocks(this);
        servlet = new AddProductServlet();
    }

    @After
    public void cleanUp() {
        deleteDataBase();
    }

    private static void executeSQLQuerySilent(String sql) throws SQLException {
        try (Connection c = DriverManager.getConnection(DATABASE_URL)) {
            Statement stmt = c.createStatement();

            stmt.executeUpdate(sql);
            stmt.close();
        }
    }

    private static String executeSQLQuery(String sql) throws SQLException, IOException {
        try (Connection c = DriverManager.getConnection(DATABASE_URL); StringWriter sw = new StringWriter()) {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String  name = rs.getString("name");
                int price  = rs.getInt("price");
                sw.write(name + " " + price + "\n");
            }
            rs.close();
            stmt.close();
            return sw.toString();
        }
    }

    private static void deleteDataBase() {
        try {
            File file = new File(DATABASE_FILENAME);
            if (file.exists() && !file.delete()) {
                throw new Exception("Cannot delete file " + DATABASE_FILENAME);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void addOneProduct() throws IOException, SQLException {
        StringWriter sw = new StringWriter();
        String productName = "product1";
        Long productPrice = 111L;
        String expectedResponse = "product1 111\n";

        when(request.getParameter("name"))
                .thenReturn(productName);
        when(request.getParameter("price"))
                .thenReturn(String.valueOf(productPrice));
        when(response.getWriter())
                .thenReturn(new PrintWriter(sw));

        servlet.doGet(request, response);

        basicVerification(sw);

        Assert.assertEquals(expectedResponse, executeSQLQuery("select * from PRODUCT"));
    }

    private void basicVerification(StringWriter sw) {
        String expectedResponse = "OK\n";
        String expectedContentType = "text/html";
        Integer expectedStatus = 200;

        verify(response).setContentType(contentTypeCaptor.capture());
        verify(response).setStatus(statusCaptor.capture());

        Assert.assertEquals(1, contentTypeCaptor.getAllValues().size());
        Assert.assertEquals(expectedContentType, contentTypeCaptor.getAllValues().get(0));
        Assert.assertEquals(1, statusCaptor.getAllValues().size());
        Assert.assertEquals(expectedStatus, statusCaptor.getAllValues().get(0));
        Assert.assertEquals(expectedResponse, sw.toString());
    }
}