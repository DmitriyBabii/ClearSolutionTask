package ua.clearsolutions.test.models.dto;

import java.sql.Date;
import java.util.UUID;


public record UserDTO(UUID id, String firstName, String lastName, String email, Date birthDate, String address,
                      String phoneNumber) {
}
