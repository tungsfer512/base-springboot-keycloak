package vn.base.app.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.base.app.model.User;
import vn.base.app.service.base.BaseService;
import vn.base.app.utils.CustomLogger;

@Service
@Transactional
public class UserService extends BaseService<User> {

    @Override
    public User save(User entity) {
        CustomLogger.info("-0999999999999999999999999999999999");
        return super.save(entity);
    }
}
