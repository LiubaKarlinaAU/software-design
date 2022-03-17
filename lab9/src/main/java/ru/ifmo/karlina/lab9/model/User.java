package ru.ifmo.karlina.lab9.model;

import org.bson.Document;

public class User {
    private final long id;
    private final Currency currency;

    public User(long id, Currency currency) {
        this.id = id;
        this.currency = currency;
    }


    public User(Document doc) {
        this(doc.getLong("id"), Currency.valueOf(doc.getString("currency")));
    }

    public long getId() {
        return id;
    }

    public Currency getCurrency() {
        return currency;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", currency='" + getCurrency().name() + '\'' +
                '}';
    }
}
