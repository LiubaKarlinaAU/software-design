package ru.ifmo.karlina.lab9;
import com.mongodb.rx.client.Success;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.reactivex.netty.protocol.http.server.HttpServer;
import ru.ifmo.karlina.lab9.model.Currency;
import ru.ifmo.karlina.lab9.model.Product;
import ru.ifmo.karlina.lab9.model.User;
import rx.Observable;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang3.EnumUtils;

public class RxNettyHTTPServer {
    public static void main(final String[] args) {
        HttpServer
                .newServer(8080)
                .start((req, resp) -> {

                    String operation = req.getDecodedPath().substring(1);
                    Map<String, List<String>> parameters = req.getQueryParameters();
                    Observable<String> response;

                    switch (operation) {
                        case "add-user" -> {
                            response = addUser(parameters);
                        }
                        case "add-product" -> {
                            response = addProduct(parameters);
                        }
                        case "get-products" -> {
                            response = getProducts(parameters);
                        }
                        case "get-users" -> {
                            response = getUsers();
                        }
                        default -> {
                            response = Observable.just("There is not such operation.\n");
                            resp.setStatus(HttpResponseStatus.BAD_REQUEST);
                            return resp.writeString(response);
                        }
                    }

                    resp.setStatus(HttpResponseStatus.OK);
                    return resp.writeString(response);
                })
                .awaitShutdown();
    }

    private static Observable<String> addUser(Map<String, List<String>> parameters) {
        if (!parameters.containsKey("id") || !parameters.containsKey("currency")) {
            return Observable.just("There is not enough parameters for this operation.\n");
        }
        String currencyValue = parameters.get("currency").get(0).toUpperCase(Locale.ROOT);
        if (!EnumUtils.isValidEnum(Currency.class, currencyValue)) {
            return Observable.just("There is not such currency.\n");
        }
        long id = Long.parseLong(parameters.get("id").get(0));
        Currency currency = Currency.valueOf(currencyValue);

        return ReactiveMongoDriver.addUser(new User(id, currency)).map(s ->
        {
            if (s.equals(Success.SUCCESS)) {
                return "New user inserted.\n";
            } else {
                return "Operation failed.\n";
            }
        });
    }

    private static Observable<String> addProduct(Map<String, List<String>> parameters) {
        if (!parameters.containsKey("name") || !parameters.containsKey("rub") ||
                !parameters.containsKey("eur") || !parameters.containsKey("usd")) {
            return Observable.just("There is not enough parameters for this operation.\n");
        }
        String name = parameters.get("name").get(0);
        double rub = Double.parseDouble(parameters.get("rub").get(0));
        double eur = Double.parseDouble(parameters.get("eur").get(0));
        double usd = Double.parseDouble(parameters.get("usd").get(0));

        return ReactiveMongoDriver.addProduct(new Product(name, rub, eur, usd)).map(s ->
        {
            if (s.equals(Success.SUCCESS)) {
                return "New product inserted.\n";
            } else {
                return "Operation failed.\n";
            }
        });
    }

    private static Observable<String> getProducts(Map<String, List<String>> parameters) {
        if (!parameters.containsKey("id")) {
            return Observable.just("There is not enough parameters for this operation.\n");
        }

        long id = Long.parseLong(parameters.get("id").get(0));
        Observable<User> user = ReactiveMongoDriver.findUser(id);
        Observable<Product> products = ReactiveMongoDriver.getAllProducts();

        return user.flatMap(u -> Observable.concat(Observable.just("Products for user with id " + u.getId() + " :\n"), products.map(p ->
            p.getName() + " price: " + p.getPrice(u.getCurrency()) + " " + u.getCurrency().name() + "\n"
        )));
    }

    private static Observable<String> getUsers() {

        Observable<User> users = ReactiveMongoDriver.getAllUsers();

        return Observable.concat(Observable.just("Users:\n"), users.map(u ->  u.toString() + "\n"));
    }
}
