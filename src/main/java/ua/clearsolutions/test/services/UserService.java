package ua.clearsolutions.test.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ua.clearsolutions.test.exceptions.ApiRequestException;
import ua.clearsolutions.test.models.entities.User;
import ua.clearsolutions.test.repositories.UserRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    @Value("${user.min-age}")
    private int age;
    private final UserRepository repository;

    public List<User> findAll() {
        return repository.findAll();
    }

    public User save(User user) {
        checkBirthDate(user.getBirthDate());
        return repository.save(user);
    }

    public User getById(UUID id) {
        return repository.getReferenceById(id);
    }

    public void deleteById(UUID id) {
        repository.deleteById(id);
    }

    public List<User> getUsersByBirthDateRange(Date fromDate, Date toDate) {
        return repository.getUsersByBirthDateRange(fromDate, toDate);
    }

    public void deleteAll() {
        repository.deleteAll();
    }

    private void checkBirthDate(Date date) {
        if (date == null) {
            throw new ApiRequestException("Birth date is missing");
        }

        LocalDate localDate = date.toLocalDate();
        if (DateUtils.isAfter(localDate) || !DateUtils.isDateAgeOld(localDate, age)) {
            throw new ApiRequestException(String.format("User must be %d years old and the date must be earlier then today.", age));
        }
    }
}
