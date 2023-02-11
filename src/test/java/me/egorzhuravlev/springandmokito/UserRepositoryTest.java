package me.egorzhuravlev.springandmokito;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepositoryTest {
    private UserRepository userRepository;
    private final User user1 = new User("user1", "pass1");
    private final User user2 = new User("user2", "pass2");

    @BeforeEach
    public void setUp() {
        userRepository = new UserRepository();
    }

    @Test
    public void getEmptyListUserTest() {
        List<User> expected = userRepository.getAllUsers();
        List<User> actual = new ArrayList<>();

        assertEquals(expected, actual);
    }

    @Test
    public void getListWithUsersTest() {
        userRepository.addUser(user1);
        userRepository.addUser(user2);
        List<User> expected = userRepository.getAllUsers();
        List<User> actual = new ArrayList<>();
        actual.add(user1);
        actual.add(user2);

        assertEquals(expected, actual);
    }

    @Test
    public void findUserByLoginWhenHasThisUser() {
        userRepository.addUser(user1);
        userRepository.addUser(user2);
        Optional<User> expected = userRepository.findUserByLogin("user1");
        List<User> userList = new ArrayList<>();
        userList.add(user1);
        userList.add(user2);
        Optional<User> actual = userList.stream()
                .filter(e -> e.getLogin().equals("user1"))
                .findAny();

        assertEquals(expected, actual);
    }

    @Test
    public void findUserByLoginWhenHasNotThisUser() {
        userRepository.addUser(user1);
        userRepository.addUser(user2);
        Optional<User> expected = userRepository.findUserByLogin("user3");
        List<User> userList = new ArrayList<>();
        userList.add(user1);
        userList.add(user2);
        Optional<User> actual = userList.stream()
                .filter(e -> e.getLogin().equals("user4"))
                .findAny();

        assertEquals(expected, actual);
    }

    @Test
    public void findUserByLoginAndPasswordWhenHasThisUser() {
        userRepository.addUser(user1);
        userRepository.addUser(user2);
        Optional<User> expected = userRepository.findUserByLoginAndPassword("user2", "pass2");
        List<User> userList = new ArrayList<>();
        userList.add(user1);
        userList.add(user2);
        Optional<User> actual = userList.stream()
                .filter(e -> e.getLogin().equals("user2") && e.getPassword().equals("pass2"))
                .findAny();

        assertEquals(expected, actual);
    }

    @Test
    public void findUserByLoginAndPasswordWhenOnlyPasswordMatches() {
        userRepository.addUser(user1);
        userRepository.addUser(user2);
        Optional<User> expected = userRepository.findUserByLoginAndPassword("user3", "pass2");
        List<User> userList = new ArrayList<>();
        userList.add(user1);
        userList.add(user2);
        Optional<User> actual = userList.stream()
                .filter(e -> e.getLogin().equals("user3") && e.getPassword().equals("pass2"))
                .findAny();

        assertEquals(expected, actual);
    }

    @Test
    public void findUserByLoginAndPasswordWhenOnlyLoginMatches() {
        userRepository.addUser(user1);
        userRepository.addUser(user2);
        Optional<User> expected = userRepository.findUserByLoginAndPassword("user1", "pass4");
        List<User> userList = new ArrayList<>();
        userList.add(user1);
        userList.add(user2);
        Optional<User> actual = userList.stream()
                .filter(e -> e.getLogin().equals("user1") && e.getPassword().equals("pass10"))
                .findAny();

        assertEquals(expected, actual);
    }
}
