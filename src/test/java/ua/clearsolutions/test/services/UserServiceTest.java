package ua.clearsolutions.test.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ua.clearsolutions.test.exceptions.ApiRequestException;
import ua.clearsolutions.test.models.entities.User;
import ua.clearsolutions.test.repositories.UserRepository;

import java.sql.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    private static final User user1 = new User("Dima", "Babii", "v.babiy75@gmail.com", Date.valueOf("2002-10-17"), "Kharkiv", "+380504021867");
    private static final User user2 = new User("Name", "Surname", "email@g", Date.valueOf("1999-01-01"), "Somewhere", "+380000000000");

    @AfterEach
    void clearUsers() {
        userService.deleteAll();
    }

    @Test
    void findAll() {
        userService.save(user1);
        userService.save(user2);
        Mockito.when(userRepository.findAll()).thenReturn(List.of(user1, user2));
        List<User> expected = userRepository.findAll().stream().sorted(new UserComparator()).toList();
        List<User> actual = userService.findAll().stream().sorted(new UserComparator()).toList();
        assertEquals(expected, actual);
    }

    @Test
    void saveWithIncorrectDate() {
        assertThrows(
                ApiRequestException.class,
                () -> userService.save(
                        new User("Name", "Surname", "e@g", Date.valueOf("2056-01-01"), "Somewhere", "+380000000001")
                ),
                "Date must be earlier then now!"
        );
    }

    @Test
    void saveWithIncorrectAge() {
        assertThrows(
                ApiRequestException.class,
                () -> userService.save(
                        new User("Name", "Surname", "e@g", Date.valueOf("2020-01-01"), "Somewhere", "+380000000001")
                ),
                "User must have 18 years old!"
        );
    }

    @Test
    void saveNewUser() {
        assertDoesNotThrow(() -> userService.save(user1));
    }

    @Test
    void deleteById() {
        User user = userService.save(user1);
        userService.deleteById(user.getId());
        assertEquals(0, userService.findAll().size());
    }

    @Test
    void getUsersByBirthDateRange() {
        userService.save(user1);
        userService.save(user2);
        List<User> users = userService.getUsersByBirthDateRange(Date.valueOf("2001-01-01"), Date.valueOf("2022-01-01"));
        assertEquals(1, users.size());
        assertEquals(user1, users.stream().findAny().orElseThrow());
    }
}