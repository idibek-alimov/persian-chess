package tj.alimov.userservice.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import tj.alimov.userservice.model.User;

public interface UserRepository extends JpaRepository<User,Long> {
}
