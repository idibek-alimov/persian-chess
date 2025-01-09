package tj.alimov.userservice.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "chess_user")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;
}
