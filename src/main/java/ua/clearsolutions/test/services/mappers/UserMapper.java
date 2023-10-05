package ua.clearsolutions.test.services.mappers;

import ua.clearsolutions.test.models.entities.User;
import ua.clearsolutions.test.models.dto.UserDTO;

public class UserMapper {
    public static User fromDTO(UserDTO userDTO) {
        return new User(userDTO.firstName(), userDTO.lastName(), userDTO.email(), userDTO.birthDate(), userDTO.address(), userDTO.phoneNumber());
    }

    public static UserDTO toDTO(User user) {
        return new UserDTO(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getBirthDate(), user.getAddress(), user.getPhoneNumber());
    }
}
