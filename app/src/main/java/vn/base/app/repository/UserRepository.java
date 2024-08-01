package vn.base.app.repository;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import vn.base.app.model.User;
import vn.base.app.repository.base.IBaseRepository;

@Repository
@Primary
public interface UserRepository extends IBaseRepository<User> {
}
