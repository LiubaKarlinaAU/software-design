package ru.akirakozov.sd.refactoring.servlet;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class GetProductsServletTest {

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Captor
    private ArgumentCaptor<String> contentTypeCaptor;
    @Captor
    private ArgumentCaptor<Integer> statusCaptor;

    private GetProductsServlet servlet;
    private static final String DATABASE_FILENAME = "test.db";
    private static final String SQL_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS PRODUCT" +
            "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            " NAME           TEXT    NOT NULL, " +
            " PRICE          INT     NOT NULL)";


    @Before
    public void setUp() throws Exception {
        deleteDataBase();
        createDataBase();
        MockitoAnnotations.initMocks(this);
        servlet = new GetProductsServlet();
    }

    @After
    public void cleanUp() throws Exception {
        deleteDataBase();
    }

    private static void createDataBase() throws SQLException {
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
            Statement stmt = c.createStatement();

            stmt.executeUpdate(SQL_CREATE_TABLE);
            stmt.close();
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
    public void emptyDataBase() throws IOException {
        StringWriter sw = new StringWriter();

        String expectedContentType = "text/html";
        Integer expectedStatus = 200;
        String expectedResponse = "<html><body>\n" +
                "</body></html>\n";

        when(response.getWriter())
                .thenReturn(new PrintWriter(sw));

        servlet.doGet(request, response);

        verify(response).setContentType(contentTypeCaptor.capture());
        verify(response).setStatus(statusCaptor.capture());

        Assert.assertEquals(expectedResponse, sw.toString());
        Assert.assertEquals(1, contentTypeCaptor.getAllValues().size());
        Assert.assertEquals(expectedContentType, contentTypeCaptor.getAllValues().get(0));
        Assert.assertEquals(1, statusCaptor.getAllValues().size());
        Assert.assertEquals(expectedStatus, statusCaptor.getAllValues().get(0));
    }
}
