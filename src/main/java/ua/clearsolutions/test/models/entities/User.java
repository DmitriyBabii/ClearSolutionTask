package ua.clearsolutions.test.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import ua.clearsolutions.test.models.dto.UserDTO;

import java.sql.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Setter(value = AccessLevel.NONE)
    private UUID id;

    @NotBlank(message = "First name must present")
    @Column(nullable = false)
    private String firstName;

    @NotBlank(message = "Last name must present")
    @Column(nullable = false)
    private String lastName;

    @NotNull(message = "Birth date must present")
    @Column(nullable = false)
    private Date birthDate;

    @NotBlank(message = "Email must present")
    @Email(message = "Invalid email")
    @Column(unique = true, nullable = false)
    private String email;

    // Optional
    @Pattern(regexp = "(\\+380)[0-9]{9}", message = "Phone number must be +380XXXXXXXXX")
    @Column(unique = true)
    private String phoneNumber;
    private String address;

    public User(String firstName, String lastName, String email, Date birthDate, String address, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.birthDate = birthDate;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public void setUser(UserDTO userDTO) {
        setFirstName(userDTO.firstName());
        setLastName(userDTO.lastName());
        setBirthDate(userDTO.birthDate());
        setEmail(userDTO.email());
        setAddress(userDTO.address());
        setPhoneNumber(userDTO.phoneNumber());
    }
}
