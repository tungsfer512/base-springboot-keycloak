package vn.base.app.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import vn.base.app.controller.base.BaseController;
import vn.base.app.model.Example;

@RestController
@RequestMapping("/api/public/examples")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "[Example]", description = "Example API")
public class ExampleController extends BaseController<Example> {

}
