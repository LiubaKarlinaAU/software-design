package ru.akirakozov.sd.refactoring.servlet;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;

import static org.mockito.Mockito.when;

public class QueryServletTest extends ServletTest {
    @Mock
    private HttpServletRequest request;
    private QueryServlet servlet;

    @Before
    public void setUp() throws Exception {
        prepareDatabase();
        MockitoAnnotations.initMocks(this);
        servlet = new QueryServlet();
    }

    @After
    public void cleanUp() {
        deleteDatabase();
    }

    @Test
    public void emptyDatabaseSumCommand() throws IOException {
        String middlePart = "Summary price: \n" + "0\n";
        String command = "sum";
        emptyDatabaseCommand(command, middlePart);
    }

    @Test
    public void emptyDatabaseCountCommand() throws IOException {
        String middlePart = "Number of products: \n" + "0\n";
        String command = "count";
        emptyDatabaseCommand(command, middlePart);
    }

    @Test
    public void emptyDatabaseMinCommand() throws IOException {
        String middlePart = "<h1>Product with min price: </h1>\n";
        String command = "min";
        emptyDatabaseCommand(command, middlePart);
    }

    @Test
    public void emptyDatabaseMaxCommand() throws IOException {
        String middlePart = "<h1>Product with max price: </h1>\n";
        String command = "max";
        emptyDatabaseCommand(command, middlePart);
    }

    @Test
    public void filledDatabaseCountCommand() throws IOException, SQLException {
        fillDatabase();
        String middlePart = "Number of products: \n" + "4\n";
        String command = "count";
        emptyDatabaseCommand(command, middlePart);
    }

    @Test
    public void filledDatabaseMaxCommand() throws IOException, SQLException {
        fillDatabase();
        String middlePart = "<h1>Product with max price: </h1>\n" + "product3\t300</br>\n";
        String command = "max";
        emptyDatabaseCommand(command, middlePart);
    }

    private void fillDatabase() throws SQLException {
        String addManyProducts = "insert into PRODUCT\n" +
                "(NAME, PRICE) values\n" +
                "(\"product1\", 100),\n" +
                "(\"product2\", 200),\n" +
                "(\"product3\", 300),\n" +
                "(\"product0\", 0)";

        executeSQLQuerySilent(addManyProducts);
    }

    private void emptyDatabaseCommand(String command, String middlePart) throws IOException {
        StringWriter sw = new StringWriter();

        when(response.getWriter())
                .thenReturn(new PrintWriter(sw));
        when(request.getParameter("command"))
                .thenReturn(command);

        servlet.doGet(request, response);

        statusAndContentTypeVerification();
        Assert.assertEquals(createExpectedResponse(middlePart), sw.toString());
    }
}