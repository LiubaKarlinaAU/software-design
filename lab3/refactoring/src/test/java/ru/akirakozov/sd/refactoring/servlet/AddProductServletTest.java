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

import static org.mockito.Mockito.when;

public class AddProductServletTest extends ServletTest {
    @Mock
    private HttpServletRequest request;
    private AddProductServlet servlet;

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
        Assert.assertEquals(expectedResponse, sw.toString());
        statusAndContentTypeVerification();
    }
}