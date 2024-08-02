package vn.base.app.repository;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import vn.base.app.model.User;
import vn.base.app.repository.base.IBaseRepository;
import java.util.Optional;

@Repository
@Primary
public interface UserRepository extends IBaseRepository<User> {
    public Optional<User> findByKeycloakId(String keycloakId);
}
