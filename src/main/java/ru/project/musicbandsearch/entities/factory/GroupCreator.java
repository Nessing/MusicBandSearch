package ru.project.musicbandsearch.entities.factory;

import ru.project.musicbandsearch.entities.users.Group;
import ru.project.musicbandsearch.entities.users.User;

public class GroupCreator implements UserFactory {
    @Override
    public User createdUser() {
        return new Group();
    }
}
