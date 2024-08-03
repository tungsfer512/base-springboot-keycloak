package vn.base.app.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.base.app.model.Example;
import vn.base.app.service.base.BaseService;

@Service
@Transactional
public class ExampleService extends BaseService<Example> {
}
