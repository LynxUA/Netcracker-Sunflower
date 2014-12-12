package com.sunflower.ejb.user;

/**
 * Created by denysburlakov on 12.12.14.
 */
public class CustomerWrapper {
    String login;
    String email;
    String name;
    String surname;

    public CustomerWrapper(String login, String email, String name, String surname) {
        this.login = login;
        this.email = email;
        this.name = name;
        this.surname = surname;
    }

    public String getLogin() {
        return login;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }
}
