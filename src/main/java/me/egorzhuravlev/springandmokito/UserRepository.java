package me.egorzhuravlev.springandmokito;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepository {
    private final List<User> users = new ArrayList<>();

    public UserRepository() {
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(users);
    }

    public Optional<User> findUserByLogin(String login) {
        return users.stream()
                .filter(e -> e.getLogin().equals(login))
                .findAny();
    }

    public Optional<User> findUserByLoginAndPassword(String login, String password) {
        return users.stream()
                .filter(e -> e.getLogin().equals(login) && e.getPassword().equals(password))
                .findAny();
    }

    public User addUser(User user) {
        users.add(user);
        return user;
    }
}
