package ru.project.musicbandsearch.entities.factory;

import ru.project.musicbandsearch.entities.users.Customer;
import ru.project.musicbandsearch.entities.users.User;

public class CustomerCreator implements UserFactory {
    @Override
    public User createdUser() {
        return new Customer();
    }
}
