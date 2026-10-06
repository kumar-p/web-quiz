package engine.persistence;

import engine.businesslayer.AppUser;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<AppUser, Long> {
    boolean existsByEmail(String email);
    Optional<AppUser> findUserByEmail(String email);
}
