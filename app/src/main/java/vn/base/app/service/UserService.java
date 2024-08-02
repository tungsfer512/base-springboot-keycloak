package vn.base.app.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.base.app.exception.CustomException;
import vn.base.app.exception.EErorr;
import vn.base.app.model.User;
import vn.base.app.repository.UserRepository;
import vn.base.app.service.base.BaseService;

@Service
@Transactional
public class UserService extends BaseService<User> {

    public User findbyKeycloakId(String keycloakId) {
        return ((UserRepository) super.getRepository()).findByKeycloakId(keycloakId)
                .orElseThrow(() -> new CustomException(EErorr.NOT_FOUND));
    }
}
