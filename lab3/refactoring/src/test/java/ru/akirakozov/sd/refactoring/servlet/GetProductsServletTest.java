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
        createDataBase(SQL_CREATE_TABLE);
        MockitoAnnotations.initMocks(this);
        servlet = new GetProductsServlet();
    }

    @After
    public void cleanUp() {
        deleteDataBase();
    }

    private static void createDataBase(String sql) throws SQLException {
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
            Statement stmt = c.createStatement();

            stmt.executeUpdate(sql);
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

        when(response.getWriter())
                .thenReturn(new PrintWriter(sw));

        servlet.doGet(request, response);

        basicVerification();
        Assert.assertEquals(createExpectedResponse(""), sw.toString());
    }

    @Test
    public void oneProductDataBase() throws IOException, SQLException {
        String addOneProduct = "insert into PRODUCT\n" +
                "(NAME, PRICE) values\n" +
                "(\"product1\", 100)";
        StringWriter sw = new StringWriter();
        String expectedResponse = createExpectedResponse("product1\t100</br>\n");

        createDataBase(addOneProduct);

        when(response.getWriter())
                .thenReturn(new PrintWriter(sw));

        servlet.doGet(request, response);

        basicVerification();
        Assert.assertEquals(expectedResponse, sw.toString());
    }

    @Test
    public void manyProductsDataBase() throws IOException, SQLException {
        String addManyProducts = "insert into PRODUCT\n" +
                "(NAME, PRICE) values\n" +
                "(\"product1\", 100),\n" +
                "(\"product2\", 200),\n" +
                "(\"product3\", 300)";
        StringWriter sw = new StringWriter();
        String expectedResponse = createExpectedResponse("product1\t100</br>\n" +
                "product2\t200</br>\n" +
                "product3\t300</br>\n");

        createDataBase(addManyProducts);

        when(response.getWriter())
                .thenReturn(new PrintWriter(sw));

        servlet.doGet(request, response);

        basicVerification();
        Assert.assertEquals(expectedResponse, sw.toString());
    }

    private static String createExpectedResponse(String middlePart) {
        return "<html><body>\n" + middlePart + "</body></html>\n";
    }

    private void basicVerification() {
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
