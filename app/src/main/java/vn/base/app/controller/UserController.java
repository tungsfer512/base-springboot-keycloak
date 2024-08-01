package vn.base.app.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.base.app.controller.base.BaseController;
import vn.base.app.model.User;

@RestController
@RequestMapping("/api/public/users")
public class UserController extends BaseController<User> {

}
