package vn.base.app.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.base.app.model.Product;
import vn.base.app.service.base.BaseService;
import vn.base.app.utils.CustomLogger;

@Service
@Transactional
public class ProductService extends BaseService<Product> {

    @Override
    public Product save(Product entity) {
        CustomLogger.info("-0999999999999999999999999999999999");
        return super.save(entity);
    }
}
