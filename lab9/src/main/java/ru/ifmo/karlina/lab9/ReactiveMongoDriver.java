package ru.ifmo.karlina.lab9;

import com.mongodb.rx.client.MongoClient;
import com.mongodb.rx.client.MongoClients;
import com.mongodb.rx.client.Success;
import org.bson.Document;
import ru.ifmo.karlina.lab9.model.Currency;
import ru.ifmo.karlina.lab9.model.Product;
import ru.ifmo.karlina.lab9.model.User;
import rx.Observable;

public class ReactiveMongoDriver {
    private static final MongoClient client = createMongoClient();
    private static final String DATABASE = "lab9";
    private static final String USER_COLLECTION = "user";
    private static final String PRODUCT_COLLECTION = "product";

    public static Observable<User> getAllUsers() {
        return  client.getDatabase(DATABASE).getCollection(USER_COLLECTION)
                .find().toObservable().map(User::new);
    }

    public static Observable<User> findUser(long id) {
        return  client.getDatabase(DATABASE).getCollection(USER_COLLECTION).find().toObservable().map(User::new).filter(u -> u.getId() == id).first();
    }

    public static Observable<Product> getAllProducts() {
        return  client.getDatabase(DATABASE).getCollection(PRODUCT_COLLECTION)
                .find().toObservable().map(Product::new);
    }

    public static Observable<Success> addProduct(Product product) {
        Document document = new Document();
        document.append("name", product.getName());
        document.append("rub", product.getPrice(Currency.RUB));
        document.append("eur", product.getPrice(Currency.EUR));
        document.append("usd", product.getPrice(Currency.USD));

          return client.getDatabase(DATABASE).getCollection(PRODUCT_COLLECTION).insertOne(document);
    }

    public static Observable<Success> addUser(User user) {
        Document document = new Document();
        document.append("id", user.getId());
        document.append("currency", user.getCurrency().name());

        return client.getDatabase(DATABASE).getCollection(USER_COLLECTION).insertOne(document);
    }


    private static MongoClient createMongoClient() {
        return MongoClients.create("mongodb://127.0.0.1:27017");
    }
}
