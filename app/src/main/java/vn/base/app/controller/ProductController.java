package vn.base.app.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.base.app.controller.base.BaseController;
import vn.base.app.model.Product;

@RestController
@RequestMapping("/api/public/products")
public class ProductController extends BaseController<Product> {

}
