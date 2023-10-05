package ua.clearsolutions.test.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.clearsolutions.test.models.dto.UserContactDTO;
import ua.clearsolutions.test.models.dto.UserDTO;
import ua.clearsolutions.test.models.entities.User;
import ua.clearsolutions.test.services.UserService;
import ua.clearsolutions.test.services.mappers.UserMapper;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/")
@AllArgsConstructor
public class UserController {
    private final UserService userService;


    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getUsers(@RequestParam(value = "from", required = false) String fromDate,
                                                  @RequestParam(value = "to", required = false) String toDate) {
        if (fromDate != null && toDate != null) {
            Date from = Date.valueOf(fromDate);
            Date to = Date.valueOf(toDate);
            if (from.after(to)) {
                Date tmp = from;
                from = to;
                to = tmp;
            }
            List<UserDTO> users = userService.getUsersByBirthDateRange(from, to).stream().map(UserMapper::toDTO).toList();
            return new ResponseEntity<>(users, HttpStatus.OK);
        }
        List<UserDTO> users = userService.findAll().stream().map(UserMapper::toDTO).toList();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping("/users")
    public ResponseEntity<String> createUser(@RequestBody UserDTO userDTO) {
        userService.save(UserMapper.fromDTO(userDTO));
        return new ResponseEntity<>("Successfully created", HttpStatus.CREATED);
    }

    @PutMapping("/users/{userId}")
    public ResponseEntity<String> updateUser(@PathVariable String userId, @RequestBody UserDTO userDTO) {
        User existingUser = userService.getById(UUID.fromString(userId));
        existingUser.setUser(userDTO);
        userService.save(existingUser);
        return new ResponseEntity<>("Successfully updated", HttpStatus.UPGRADE_REQUIRED);
    }

    @PatchMapping("/users/{userId}")
    public ResponseEntity<String> updateUser(@PathVariable String userId, @RequestBody UserContactDTO
            userContactDTO) {
        User existingUser = userService.getById(UUID.fromString(userId));
        existingUser.setEmail(userContactDTO.email());
        existingUser.setPhoneNumber(userContactDTO.phoneNumber());
        existingUser.setAddress(userContactDTO.address());
        userService.save(existingUser);
        return new ResponseEntity<>("Successfully updated", HttpStatus.UPGRADE_REQUIRED);
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable String userId) {
        userService.deleteById(UUID.fromString(userId));
        return new ResponseEntity<>("Successfully deleted", HttpStatus.NO_CONTENT);
    }
}
