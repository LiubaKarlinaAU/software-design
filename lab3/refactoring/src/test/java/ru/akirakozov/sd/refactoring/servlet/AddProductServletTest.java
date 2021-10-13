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
import java.sql.*;

import static org.mockito.Mockito.*;

public class AddProductServletTest extends ServletTest {
    @Mock
    private HttpServletRequest request;
    private AddProductServlet servlet;
    private static final String SQL_QUERY_SELECT_ALL = "select * from PRODUCT";

    @Before
    public void setUp() throws Exception {
        prepareDatabase();
        MockitoAnnotations.initMocks(this);
        servlet = new AddProductServlet();
    }

    @After
    public void cleanUp() {
        deleteDatabase();
    }

    @Test
    public void addOneProduct() throws IOException, SQLException {
        String productName = "add-product1";
        Long productPrice = 111L;
        String expectedResponse = productName + " " + productPrice + "\n";

        addProduct(productName, productPrice);

        Assert.assertEquals(expectedResponse, executeSQLQuery(SQL_QUERY_SELECT_ALL));
    }

    @Test
    public void addAlreadyExistingProduct() throws IOException, SQLException {
        String productName = "add-product2";
        long productPrice = 100L;
        String expectedResponse = "add-product2 100\n" +
                "add-product2 100\n";
        executeSQLQuerySilent(makeInsertQuery(productName, productPrice));

        addProduct(productName, productPrice);
        Assert.assertEquals(expectedResponse, executeSQLQuery(SQL_QUERY_SELECT_ALL));
    }

    private void addProduct(String productName, Long productPrice) throws IOException {
        StringWriter sw = new StringWriter();

        when(request.getParameter("name"))
                .thenReturn(productName);
        when(request.getParameter("price"))
                .thenReturn(String.valueOf(productPrice));
        when(response.getWriter())
                .thenReturn(new PrintWriter(sw));

        servlet.doGet(request, response);
        basicVerification(sw);
    }

    private void basicVerification(StringWriter sw) {
        String expectedResponse = "OK\n";
        Assert.assertEquals(expectedResponse, sw.toString());
        statusAndContentTypeVerification();
    }
}