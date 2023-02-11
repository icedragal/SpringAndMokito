package me.egorzhuravlev.springandmokito;

import me.ildar.mockito.exception.UserNonUniqueException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    private final User user1 = new User("user1", "pass1");
    private final User user2 = new User("user2", "pass2");
    @Mock
    UserRepository userRepository;
    @InjectMocks
    UserService userService;

    @Test
    public void getEmptyListLoginTest() {
        when(userRepository.getAllUsers()).thenReturn(List.of());

        assertThat(userService.getLogins()).isEqualTo(new ArrayList<String>());
    }

    @Test
    public void getNotEmptyListLoginTest() {
        when(userRepository.getAllUsers()).thenReturn(List.of(user1, user2));

        assertThat(userService.getLogins()).isEqualTo(List.of(user1.getLogin(), user2.getLogin()));
    }

    @Test
    public void createNewUserTest() {
        when(userRepository.addUser(any(User.class))).thenReturn(user1);
        userService.createNewUser("user1", "pass1");

        verify(userRepository).addUser(any());
    }

    @Test
    void whenCreateNewUserReturnNullTest() {
        when(userRepository.addUser(any(User.class))).thenReturn(null);
        userService.createNewUser("user1", "pass1");

        verify(userRepository).addUser(any());
    }

    @Test
    void whenInvalidUserIsPassedThenServiceThrowsException() {
        assertThatThrownBy(() -> userService.createNewUser("", ""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Поля логин и пароль не должны быть пустыми!");

        verify(userRepository, never()).getAllUsers();
        verify(userRepository, never()).addUser(any());
    }

    @Test
    void createNewUserWhenAlreadyHasUserTest() {
        when(userRepository.getAllUsers()).thenReturn(List.of(user2));
        assertThatThrownBy(() -> userService.createNewUser("user2", "pass2"))
                .isInstanceOf(UserNonUniqueException.class)
                .hasMessage("Пользователь с таким логином уже существует");

        verify(userRepository).getAllUsers();
        verify(userRepository, never()).addUser(any());
    }

    @Test
    void logInUserTest() {
        when(userRepository.getAllUsers()).thenReturn(List.of(user1, user2));

        assertThat(userService.logInUser("user1", "pass1")).isTrue();
    }
}
