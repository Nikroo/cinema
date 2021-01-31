package by.itacademy.service.user;

import by.itacademy.service.util.Hash;

public class User {
    private Integer id;

    private String login;
    private String password;
    private String hash = "0";
    private Role role;

    public User() {
    }

    public User(String login, String password, Role role) {
        this.hash = Hash.createHash(password);
        this.login = login;
        this.password = password;
        this.role = role;
    }

    public User(Integer id, String login, String password, Role role) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.role = role;
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

    public Role getRole() {
        return role;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", hash='" + hash + '\'' +
                ", role=" + role +
                '}';
    }
    public static class Role {

        private int id;
        private String name;

        public Role(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Role{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }
    }
}
