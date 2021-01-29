package by.itacademy.service.user;

import by.itacademy.service.util.Hash;

public abstract class User {
    private Integer id;
    private String login;
    private String password;
    private String hash = "0";

    public User (Integer id, String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getHash() { return hash; }

    public User(String login, String password) {
        this.hash = Hash.createHash(password);
        this.login = login;
        this.password = password;
    }
}
