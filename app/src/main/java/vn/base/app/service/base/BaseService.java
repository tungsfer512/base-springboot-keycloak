package vn.base.app.service.base;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.Nullable;
import vn.base.app.exception.CustomException;
import vn.base.app.exception.EErorr;
import vn.base.app.model.base.BaseModel;
import vn.base.app.repository.base.IBaseRepository;
import vn.base.app.utils.CustomPagination;
import vn.base.app.utils.CustomSpecification;
import vn.base.app.utils.Utils;
import vn.base.app.utils.constants.EComparision;

@Service
@Transactional
public class BaseService<T extends BaseModel> implements IBaseService<T> {

    @Autowired
    IBaseRepository<T> repository;

    public IBaseRepository<T> getRepository() {
        return this.repository;
    }

    Sort getSorts(@Nullable List<Pair<String, String>> sortConditions) {
        List<Order> orders = new ArrayList<>();
        if (sortConditions != null) {
            for (Pair<String, String> sortCondition : sortConditions) {
                String attribute = sortCondition.getLeft();
                String sortDirection = sortCondition.getRight();
                if (sortDirection.equalsIgnoreCase("ASC")) {
                    orders.add(new Order(Sort.Direction.ASC, attribute));
                } else if (sortDirection.equalsIgnoreCase("DESC")) {
                    orders.add(new Order(Sort.Direction.DESC, attribute));
                }
            }
        }
        Sort sorts = Sort.by(orders);
        return sorts;
    }

    Specification<T> getFilters(@Nullable List<Triple<String, List<Object>, EComparision>> filterConditions) {
        CustomSpecification<T> customConditions = new CustomSpecification<>();
        Specification<T> conditions = new CustomSpecification<>();
        if (filterConditions != null) {
            for (Triple<String, List<Object>, EComparision> filterCondition : filterConditions) {
                String attribute = filterCondition.getLeft();
                List<Object> values = filterCondition.getMiddle();
                EComparision comparison = filterCondition.getRight();
                conditions = conditions.and(customConditions);
                if (values.size() < 1) {
                    continue;
                } else if (values.size() == 1) {
                    Object value = values.get(0);
                    conditions = conditions.and(customConditions.condition(attribute, comparison, value));
                } else {
                    Object value_start = values.get(0);
                    Object value_end = values.get(1);
                    Pair<Object, Object> value = Pair.of(value_start, value_end);
                    conditions = conditions
                            .and(customConditions.condition_between(attribute, comparison, value));
                }
            }
        }
        return conditions;
    }

    @Override
    public List<T> findAll() {
        return repository.findAll();
    }

    @Override
    public List<T> findAll(@Nullable List<Pair<String, String>> sortConditions) {
        Sort sorts = getSorts(sortConditions);
        return repository.findAll(sorts);
    }

    @Override
    public List<T> findAll(
            @Nullable List<Triple<String, List<Object>, EComparision>> filterConditions,
            @Nullable List<Pair<String, String>> sortConditions) {
        Sort sorts = getSorts(sortConditions);
        Specification<T> conditions = getFilters(filterConditions);
        return repository.findAll(conditions, sorts);
    }

    @Override
    public List<T> findAll(
            @Nullable CustomPagination pagination,
            @Nullable List<Triple<String, List<Object>, EComparision>> filterConditions,
            @Nullable List<Pair<String, String>> sortConditions) {
        if (pagination == null) {
            pagination = new CustomPagination();
        }
        Sort sorts = getSorts(sortConditions);
        Pageable pageable = PageRequest.of((((int) pagination.getPage()) - 1), ((int) pagination.getPageSize()), sorts);
        Specification<T> conditions = getFilters(filterConditions);
        return repository.findAll(conditions, pageable).getContent();
    }

    @Override
    public long count() {
        return repository.count();
    }

    @Override
    public long count(@Nullable List<Triple<String, List<Object>, EComparision>> filterConditions) {
        Specification<T> conditions = getFilters(filterConditions);
        return repository.count(conditions.and(conditions));
    }

    @Override
    public T findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new CustomException(EErorr.NOT_FOUND));
    }

    @Override
    public Optional<T> findByIdOptional(Long id) {
        return repository.findById(id);
    }

    @Override
    public T save(T entity) {
        if (entity.getId() != null) {
            repository.findById(entity.getId()).orElseThrow(() -> new CustomException(EErorr.NOT_FOUND));
            entity.setUpdatedAt(Utils.TIMESTAMP_NOW());
        }
        return repository.save(entity);
    }

    @Override
    public T save(T entity, Boolean partially) throws Exception {
        if (entity.getId() != null) {
            T existedEntity = repository.findById(entity.getId())
                    .orElseThrow(() -> new CustomException(EErorr.NOT_FOUND));
            entity.setUpdatedAt(Utils.TIMESTAMP_NOW());
            if (partially != null && partially == true) {
                List<Field> fields = Utils.GET_ALL_FIELD_OF_OBJECT(existedEntity);
                for (Field field : fields) {
                    String fieldName = field.getName();
                    if (Utils.GET_OBJECT_FIELD_VALUE(entity, fieldName) == null) {
                        Utils.SET_OBJECT_FIELD_VALUE(entity, fieldName,
                                Utils.GET_OBJECT_FIELD_VALUE(existedEntity, fieldName));
                    }
                }
            }
        }
        return repository.save(entity);
    }

    @Override
    public void delete(T entity) {
        repository.findById(entity.getId()).orElseThrow(() -> new CustomException(EErorr.NOT_FOUND));
        repository.delete(entity);
    }

    @Override
    public void deleteById(Long id) {
        repository.findById(id).orElseThrow(() -> new CustomException(EErorr.NOT_FOUND));
        repository.deleteById(id);
    }

}
