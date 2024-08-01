package vn.base.app.service.base;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import jakarta.annotation.Nullable;
import vn.base.app.model.base.BaseModel;
import vn.base.app.utils.CustomPagination;
import vn.base.app.utils.constants.EComparision;

public interface IBaseService<T extends BaseModel> {

    public List<T> findAll();

    public List<T> findAll(@Nullable List<Pair<String, String>> sortConditions);

    public List<T> findAll(
            @Nullable List<Triple<String, List<Object>, EComparision>> filterConditions,
            @Nullable List<Pair<String, String>> sortConditions);

    public List<T> findAll(
            @Nullable CustomPagination pagination,
            @Nullable List<Triple<String, List<Object>, EComparision>> filterConditions,
            @Nullable List<Pair<String, String>> sortConditions);

    public long count();

    public long count(@Nullable List<Triple<String, List<Object>, EComparision>> filterConditions);

    public T findById(String id);

    public T save(T entity);

    public T save(T entity, Boolean partially) throws Exception;

    public void delete(T entity);

    public void deleteById(String id);

}
