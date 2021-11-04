package ru.itmo.karlina.lab7.domain;

import ru.itmo.karlina.lab7.aspect.Profile;
import ru.itmo.karlina.lab7.dao.CustomerInMemoryDao;

public class CustomerManagerImpl implements CustomerManager {
    CustomerInMemoryDao customerDao;

    public CustomerManagerImpl(CustomerInMemoryDao customerDao) {
        this.customerDao = customerDao;
    }

    @Profile
    public int addCustomer(Customer customer) {
        return customerDao.addCustomer(customer);
    }

    @Profile
    public Customer findCustomer(int id) {
        return customerDao.findCustomer(id);
    }
}
