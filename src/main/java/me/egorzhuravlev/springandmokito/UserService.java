package me.egorzhuravlev.springandmokito;

import me.ildar.mockito.exception.UserNonUniqueException;

import java.util.List;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<String> getLogins() {

        return userRepository
                .getAllUsers()
                .stream()
                .map(User::getLogin)
                .toList();
    }

    public void createNewUser(String login, String password) {
        User user = new User(login, password);

        if (login == null || login.isEmpty() || password == null || password.isEmpty()) {
            throw new IllegalArgumentException("поля логин и пароль не должны быть пустыми!");
        }

        boolean userExist = userRepository
                .getAllUsers()
                .stream()
                .anyMatch(u -> u.equals(user));
        if (userExist) {
            throw new UserNonUniqueException("пользователь с таким логином уже существует");
        }
        userRepository.addUser(user);
    }

    public boolean logInUser(String login, String password) {
        return this.userRepository
                .getAllUsers()
                .stream()
                .anyMatch(e -> e.getLogin().equals(login) && e.getPassword().equals(password));
    }
}