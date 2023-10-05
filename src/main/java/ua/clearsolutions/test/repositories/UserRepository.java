package ua.clearsolutions.test.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.clearsolutions.test.models.entities.User;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    @Query("SELECT u FROM User u WHERE u.birthDate BETWEEN :from AND :to")
    List<User> getUsersByBirthDateRange(@Param("from") Date fromDate, @Param("to") Date toDate);
}
