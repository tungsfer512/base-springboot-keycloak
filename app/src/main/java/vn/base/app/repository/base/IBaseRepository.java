package vn.base.app.repository.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import vn.base.app.model.base.BaseModel;

@NoRepositoryBean
public interface IBaseRepository<T extends BaseModel> extends JpaRepository<T, String>, JpaSpecificationExecutor<T> {
}
