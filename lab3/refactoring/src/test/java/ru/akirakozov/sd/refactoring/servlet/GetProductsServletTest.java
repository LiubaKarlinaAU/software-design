package ru.akirakozov.sd.refactoring.servlet;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;

public class GetProductsServletTest extends ServletTest {
    @Mock
    private HttpServletRequest request;
    private GetProductsServlet servlet;

    @Before
    public void setUp() throws Exception {
        prepareDatabase();
        MockitoAnnotations.initMocks(this);
        servlet = new GetProductsServlet();
    }

    @After
    public void cleanUp() {
        deleteDatabase();
    }

    @Test
    public void emptyDataBase() throws IOException {
        StringWriter sw = new StringWriter();

        when(response.getWriter())
                .thenReturn(new PrintWriter(sw));

        servlet.doGet(request, response);

        statusAndContentTypeVerification();
        Assert.assertEquals(createExpectedResponse(""), sw.toString());
    }

    @Test
    public void oneProductDataBase() throws IOException, SQLException {
        String productName = "get-product2";
        long productPrice = 100L;
        StringWriter sw = new StringWriter();
        String expectedResponse = createExpectedResponse(productName + "\t" + productPrice + "</br>\n");

        executeSQLQuerySilent(makeInsertQuery(productName, productPrice));

        when(response.getWriter())
                .thenReturn(new PrintWriter(sw));

        servlet.doGet(request, response);

        statusAndContentTypeVerification();
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

        executeSQLQuerySilent(addManyProducts);

        when(response.getWriter())
                .thenReturn(new PrintWriter(sw));

        servlet.doGet(request, response);

        statusAndContentTypeVerification();
        Assert.assertEquals(expectedResponse, sw.toString());
    }
}
