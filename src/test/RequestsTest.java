package test;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.ConnectException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.stream.Collectors;


class RequestsTest extends ServerTest {
    private final static String HOST = "localhost";
    private final static int PORT = 8081;

    private final HttpClient httpClient = HttpClient.newHttpClient();

    @Test
    public void empty() {
        Assert.assertEquals(getProducts(),
                "<html><body>\n" +
                        "</body></html>\n");
    }

    @Test
    public void addTV() {
        Assert.assertEquals(addProduct("limon", 100500),
                "OK\n");
    }

    @Test
    public void addSomeProducts() {
        addProduct("limon", 100500);
        addProduct("milk", 100500);
        Assert.assertEquals(getProducts(),
                "<html><body>\n" +
                        "limon\t100500</br>\n" +
                        "milk\t100500</br>\n" +
                        "</body></html>\n");
    }


    @Test(dataProvider = "commands")
    public void mounyTest(String command, String result) {
        addProduct("milk", 2000);
        addProduct("limon", 100);
        Assert.assertEquals(query(command), result);
    }

    private String addProduct(String name, long price) {
        return sendRequest("add-product", Map.of(
                "name", name,
                "price", price
        ));
    }
    private String getProducts() {
        return sendRequest("get-products", Map.of());
    }

    private String query(String command) {
        return sendRequest("query", Map.of(
                "command", command
        ));
    }

    //    commands for checking mounyTest
    @DataProvider(name = "commands")
    public Object[][] commands() {
        return new Object[][]{
                new Object[]{"sum", "<html><body>\n" +
                        "Summary price: \n" +
                        "100\n" +
                        "</body></html>\n"},
                new Object[]{"count", "<html><body>\n" +
                        "Number of products: \n" +
                        "2\n" +
                        "</body></html>\n"},
                new Object[]{"max", "<html><body>\n" +
                        "<h1>Product with max price: </h1>\n" +
                        "limon\t100</br>\n" +
                        "</body></html>\n"},
                new Object[]{"min", "<html><body>\n" +
                        "<h1>Product with min price: </h1>\n" +
                        "milk\t2000</br>\n" +
                        "</body></html>\n"},
                new Object[]{"unknown-command", "Unknown command: unknown-command\n"},
        };
    }

    private String sendRequest(String method, Map<String, Object> params) {
        String requestParam = params.entrySet().stream().map(e -> e.getKey() + "=" + e.getValue()).collect(Collectors.joining("&"));
        String uri = "http://localhost:" + 8081 + "/" + method + (params.isEmpty() ? "" : "?" + requestParam);
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(uri)).build();
        while (true) {
            try {
                return httpClient.send(request, HttpResponse.BodyHandlers.ofString()).body();
            } catch (ConnectException ignored) {
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
                return "";
            }
        }
    }
}
