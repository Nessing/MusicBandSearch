package ru.project.musicbandsearch.entities.factory;

import ru.project.musicbandsearch.entities.users.Musician;
import ru.project.musicbandsearch.entities.users.User;

public class MusicianCreator implements UserFactory {
    @Override
    public User createdUser() {
        return new Musician();
    }
}
