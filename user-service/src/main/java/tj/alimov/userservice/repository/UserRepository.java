package tj.alimov.userservice.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import tj.alimov.userservice.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> getUserById(Long id);

    Optional<User> getUserByName(String name);
}
