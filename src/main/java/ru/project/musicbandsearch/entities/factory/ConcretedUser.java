package ru.project.musicbandsearch.entities.factory;

import static ru.project.musicbandsearch.entities.factory.UserType.*;

public class ConcretedUser {
    public static UserFactory getConcretedUser(String typeRole){
        if (typeRole.equals(MUSICIAN)){
            return new MusicianCreator();
        }
        if (typeRole.equals(CUSTOMER)){
            return new CustomerCreator();
        }
        if (typeRole.equals(GROUP)){
            return new GroupCreator();
        }
        throw new RuntimeException("ПОЛЬЗОВАТЕЛЯ С РОЛЬЮ " + typeRole + " НЕ СУЩЕСТВУЕТ");
    }
}
