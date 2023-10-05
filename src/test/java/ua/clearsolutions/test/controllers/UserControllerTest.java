package ua.clearsolutions.test.controllers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ua.clearsolutions.test.models.entities.User;
import ua.clearsolutions.test.repositories.UserRepository;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;

    private static final String TEXT_TYPE = "text/plain;charset=UTF-8";

    private static final User user = new User("Dima", "Babii", "v.babiy75@gmail.com", Date.valueOf("2002-10-17"), "Address", "+380504021867");

    @BeforeEach
    void setUp() {
        userRepository.save(user);
    }

    @AfterEach
    void clear() {
        userRepository.deleteAll();
    }

    @Test
    void getUsers() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void createUser() throws Exception {
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "firstName": "Dima",
                                    "lastName": "Babii",
                                    "email": "email@g",
                                    "birthDate": "2002-10-17",
                                    "address": "Somewhere",
                                    "phoneNumber": "+380000000001"
                                }"""))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.valueOf(TEXT_TYPE)));
    }

    @Test
    void createUserWithIncorrectEmail() throws Exception {
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "firstName": "Dima",
                                    "lastName": "Babii",
                                    "email": "emailg",
                                    "birthDate": "2002-10-17",
                                    "address": "Somewhere",
                                    "phoneNumber": "+380000000001"
                                }"""))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void createUserWithIncorrectPhone() throws Exception {
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "firstName": "Dima",
                                    "lastName": "Babii",
                                    "email": "email@g",
                                    "birthDate": "2002-10-17",
                                    "address": "Somewhere",
                                    "phoneNumber": "+381000000001"
                                }"""))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void createUserWithIncorrectBirthDate() throws Exception {
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "firstName": "Dima",
                                    "lastName": "Babii",
                                    "email": "emailg",
                                    "birthDate": "2022-10-17",
                                    "address": "Somewhere",
                                    "phoneNumber": "+380000000001"
                                }"""))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void updateFullUser() throws Exception {
        mockMvc.perform(put("/users/" + user.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "firstName": "Dima",
                                    "lastName": "Babiy",
                                    "email": "email@g",
                                    "birthDate": "2002-10-17",
                                    "address": "Somewhere",
                                    "phoneNumber": "+380000000002"
                                }"""))
                .andExpect(status().isUpgradeRequired())
                .andExpect(content().contentType(MediaType.valueOf(TEXT_TYPE)));
    }

    @Test
    void updateFullUserWithIncorrectEmail() throws Exception {
        mockMvc.perform(put("/users/" + user.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "firstName": "Dima",
                                    "lastName": "Babiy",
                                    "email": "emailg",
                                    "birthDate": "2002-10-17",
                                    "address": "Somewhere",
                                    "phoneNumber": "+380000000002"
                                }"""))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void updateFullUserWithIncorrectDate() throws Exception {
        mockMvc.perform(put("/users/" + user.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "firstName": "Dima",
                                    "lastName": "Babiy",
                                    "email": "email@g",
                                    "birthDate": "2022-10-17",
                                    "address": "Somewhere",
                                    "phoneNumber": "+380000000002"
                                }"""))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void updateFullUserWithIncorrectPhone() throws Exception {
        mockMvc.perform(put("/users/" + user.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "firstName": "Dima",
                                    "lastName": "Babiy",
                                    "email": "email@g",
                                    "birthDate": "2002-10-17",
                                    "address": "Somewhere",
                                    "phoneNumber": "+381000000002"
                                }"""))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void updateSomeUserFields() throws Exception {
        mockMvc.perform(patch("/users/" + user.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "email": "email@g",
                                    "address": "Somewhere",
                                    "phoneNumber": "+380000000002"
                                }"""))
                .andExpect(status().isUpgradeRequired())
                .andExpect(content().contentType(MediaType.valueOf(TEXT_TYPE)));
    }

    @Test
    void updateSomeUserFieldsWithIncorrectEmail() throws Exception {
        mockMvc.perform(patch("/users/" + user.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "email": "emailg",
                                    "address": "Somewhere",
                                    "phoneNumber": "+380000000002"
                                }"""))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void updateSomeUserFieldsWithIncorrectPhone() throws Exception {
        mockMvc.perform(patch("/users/" + user.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "email": "email@g",
                                    "address": "Somewhere",
                                    "phoneNumber": "+381000000002"
                                }"""))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void deleteUser() throws Exception {
        mockMvc.perform(delete("/users/" + user.getId()))
                .andExpect(status().isNoContent())
                .andExpect(content().contentType(MediaType.valueOf(TEXT_TYPE)));
        assertEquals(0, userRepository.findAll().size());
    }
}