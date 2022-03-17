package ru.ifmo.karlina.lab9.model;

import org.bson.Document;

import java.util.HashMap;
import java.util.Map;

public class Product {
    private final String name;
    private final Map<Currency, Double> prices;

    public Product(String name, Map<Currency, Double> prices) {
        this.name = name;
        this.prices = prices;
    }

    public Product(String name, double rub, double eur, double usd) {
        this.name = name;
        this.prices = new HashMap<>();
        prices.put(Currency.RUB, rub);
        prices.put(Currency.EUR, eur);
        prices.put(Currency.USD, usd);
    }

    public Product(Document doc) {
        this(doc.getString("name"), doc.getDouble("rub"), doc.getDouble("eur"), doc.getDouble("usd"));
    }

    public String getName() {
        return name;
    }

    public double getPrice(Currency currency) {
        return prices.get(currency);
    }

    @Override
    public String toString() {
        return "Product{" +
                "name=" + name +
                ", rub='" + prices.get(Currency.RUB) + '\'' +
                ", eur='" + prices.get(Currency.EUR) + '\'' +
                ", usd='" + prices.get(Currency.USD) + '\'' +
                '}';
    }
}
