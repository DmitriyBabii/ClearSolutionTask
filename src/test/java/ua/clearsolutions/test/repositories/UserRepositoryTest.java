package ua.clearsolutions.test.repositories;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.TransactionSystemException;
import ua.clearsolutions.test.models.entities.User;
import ua.clearsolutions.test.services.UserComparator;

import java.sql.Date;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    private final static User user1 = new User("Dima", "Babii", "v.babiy75@gmail.com", Date.valueOf("2002-10-17"), "Kharkiv", "+380504021867");
    private final static User user2 = new User("Name", "Surname", "email@g", Date.valueOf("1999-01-01"), "Somewhere", "+380000000000");


    @BeforeEach
    void createUsers() {
        userRepository.save(user1);
        userRepository.save(user2);
    }

    @AfterEach
    void clearUsers() {
        userRepository.deleteAll();
    }

    @Test
    void saveUser() {
        assertEquals(2, userRepository.findAll().size(), "Must be 2 users");
    }

    @Test
    void saveUserWithEmptyFirstName() {
        User user = getUserTemplate();
        user.setFirstName("");
        assertThrows(TransactionSystemException.class, () -> userRepository.save(user));
    }

    @Test
    void saveUserWithNullFirstName() {
        User user = getUserTemplate();
        user.setFirstName(null);
        assertThrows(TransactionSystemException.class, () -> userRepository.save(user));
    }

    @Test
    void saveUserWithEmptyLastName() {
        User user = getUserTemplate();
        user.setLastName("");
        assertThrows(TransactionSystemException.class, () -> userRepository.save(user));
    }

    @Test
    void saveUserWithNullLastName() {
        User user = getUserTemplate();
        user.setLastName(null);
        assertThrows(TransactionSystemException.class, () -> userRepository.save(user));
    }

    @Test
    void saveUserWithNullEmail() {
        User user = getUserTemplate();
        user.setEmail(null);
        assertThrows(TransactionSystemException.class, () -> userRepository.save(user));
    }

    @Test
    void saveUserWithEmptyEmail() {
        User user = getUserTemplate();
        user.setEmail("");
        assertThrows(TransactionSystemException.class, () -> userRepository.save(user));
    }

    @Test
    void saveUserWithInvalidEmail() {
        User user = getUserTemplate();
        user.setEmail("email");
        assertThrows(TransactionSystemException.class, () -> userRepository.save(user));
    }

    @Test
    void saveUserWithNonUniqueEmail() {
        User user = getUserTemplate();
        user.setEmail(user2.getEmail());
        assertThrows(DataIntegrityViolationException.class, () -> userRepository.save(user));
    }

    @Test
    void saveUserWithInvalidDate() {
        User user = getUserTemplate();
        assertThrows(IllegalArgumentException.class, () -> {
            user.setBirthDate(Date.valueOf("20w22-10-17"));
            userRepository.save(user);
        });
    }

    @Test
    void saveUserWithNullDate() {
        User user = getUserTemplate();
        user.setBirthDate(null);
        assertThrows(TransactionSystemException.class, () -> userRepository.save(user));
    }

    @Test
    void saveUserWithNullPhone() {
        User user = getUserTemplate();
        user.setPhoneNumber(null);
        assertNotNull(userRepository.save(user));
    }

    @Test
    void saveUserWithIncorrectPhone() {
        User user = getUserTemplate();
        user.setPhoneNumber("-381000000001");
        assertThrows(TransactionSystemException.class, () -> userRepository.save(user));
    }

    @Test
    void saveUserWithNonUniquePhone() {
        User user = getUserTemplate();
        user.setPhoneNumber("+380504021867");
        assertThrows(DataIntegrityViolationException.class, () -> userRepository.save(user));
    }

    @Test
    void findAllUsers() {
        List<User> expected = Stream.of(user1, user2).sorted(new UserComparator()).toList();
        List<User> actual = userRepository.findAll().stream().sorted(new UserComparator()).toList();
        assertEquals(expected, actual, "Wrong users");
    }

    @Test
    void getUsersByBirthDateRange() {
        List<User> users = userRepository.getUsersByBirthDateRange(Date.valueOf("2000-01-01"), Date.valueOf("2010-10-10"));
        assertEquals(1, users.size(), "Must be only 1 user");
    }

    @Test
    void getUserById() {
        User user = userRepository.findById(user1.getId()).orElseThrow();
        assertEquals(user, user1);
    }

    User getUserTemplate() {
        return new User("Dima", "Babii", "e@g", Date.valueOf("2002-10-17"), "Kharkiv", "+380000000001");
    }
}