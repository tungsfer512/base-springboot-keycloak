package vn.base.app.repository;

import org.springframework.stereotype.Repository;

import vn.base.app.model.Product;
import vn.base.app.repository.base.IBaseRepository;

@Repository
public interface ProductRepository extends IBaseRepository<Product> {
}
