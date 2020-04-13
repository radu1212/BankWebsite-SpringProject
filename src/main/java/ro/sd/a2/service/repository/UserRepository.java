package ro.sd.a2.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ro.sd.a2.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    //@Query( value = "SELECT * FROM User t WHERE t.username = ?1", nativeQuery = true)
    User findByUsername(String username);

    //@Query( value = "SELECT * FROM User t WHERE t.email = ?1", nativeQuery = true)
    User findByEmail(String email);
}
