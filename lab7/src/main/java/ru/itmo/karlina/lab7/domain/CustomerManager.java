package ru.itmo.karlina.lab7.domain;

public interface CustomerManager {
    int addCustomer(Customer customer);
    Customer findCustomer(int id);
}
