package vn.base.app.utils;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import vn.base.app.utils.constants.EComparision;

public class CustomSpecification<T> implements Specification<T> {

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.conjunction();
    }

    public Specification<T> condition(String attribute, EComparision comparison, Object value) {
        return (root, query, builder) -> {
            String[] attributes = attribute.split("\\.");
            Path<Object> object_value = root.get(attributes[0]);
            for (int i = 1; i < attributes.length; i++) {
                object_value = object_value.get(attributes[i]);
            }
            if (value == null) {
                return null;
            }
            if (comparison == EComparision.EQUAL) {
                return builder.equal(object_value.as(value.getClass()), value);
            } else if (comparison == EComparision.NOT_EQUAL) {
                return builder.notEqual(object_value.as(value.getClass()), value);
            }
            if (value instanceof String) {
                String string = (String) value;
                if (comparison == EComparision.START_WITH) {
                    return builder.like(object_value.as(String.class), string + "%");
                } else if (comparison == EComparision.END_WITH) {
                    return builder.like(object_value.as(String.class), "%" + value);
                } else if (comparison == EComparision.LIKE) {
                    return builder.like(object_value.as(String.class), "%" + string + "%");
                } else if (comparison == EComparision.NOT_START_WITH) {
                    return builder.notLike(object_value.as(String.class), string + "%");
                } else if (comparison == EComparision.NOT_END_WITH) {
                    return builder.notLike(object_value.as(String.class), "%" + value);
                } else if (comparison == EComparision.NOT_LIKE) {
                    return builder.notLike(object_value.as(String.class), "%" + string + "%");
                } else if (comparison == EComparision.GREATER_THAN) {
                    return builder.greaterThan(object_value.as(String.class), string);
                } else if (comparison == EComparision.GREATER_EQUAL_THAN) {
                    return builder.greaterThanOrEqualTo(object_value.as(String.class), string);
                } else if (comparison == EComparision.LESS_THAN) {
                    return builder.lessThan(object_value.as(String.class), string);
                } else if (comparison == EComparision.LESS_EQUAL_THAN) {
                    return builder.lessThanOrEqualTo(object_value.as(String.class), string);
                }
            } else if (value instanceof Number) {
                Double number = (Double) value;
                if (comparison == EComparision.GREATER_THAN) {
                    return builder.greaterThan(object_value.as(Double.class), number);
                } else if (comparison == EComparision.GREATER_EQUAL_THAN) {
                    return builder.greaterThanOrEqualTo(object_value.as(Double.class), number);
                } else if (comparison == EComparision.LESS_THAN) {
                    return builder.lessThan(object_value.as(Double.class), number);
                } else if (comparison == EComparision.LESS_EQUAL_THAN) {
                    return builder.lessThanOrEqualTo(object_value.as(Double.class), number);
                }
            } else if (value instanceof Date) {
                Date date = (Date) value;
                if (comparison == EComparision.GREATER_THAN) {
                    return builder.greaterThan(object_value.as(Date.class), date);
                } else if (comparison == EComparision.GREATER_EQUAL_THAN) {
                    return builder.greaterThanOrEqualTo(object_value.as(Date.class), date);
                } else if (comparison == EComparision.LESS_THAN) {
                    return builder.lessThan(object_value.as(Date.class), date);
                } else if (comparison == EComparision.LESS_EQUAL_THAN) {
                    return builder.lessThanOrEqualTo(object_value.as(Date.class), date);
                }
                return null;
            }
            return null;
        };
    }

    public Specification<T> condition_in(String attribute, EComparision comparison, List<Object> values) {
        return (root, query, builder) -> {
            String[] attributes = attribute.split("\\.");
            Path<Object> object_value = root.get(attributes[0]);
            for (int i = 1; i < attributes.length; i++) {
                object_value = object_value.get(attributes[i]);
            }
            if (values == null || values.size() <= 0) {
                return null;
            }
            if (comparison == EComparision.CONTAINS) {
                return object_value.in(values);
            }
            return null;
        };
    }

    public Specification<T> condition_between(String attribute, EComparision comparison,
            Pair<Object, Object> value) {
        return (root, query, builder) -> {
            String[] attributes = attribute.split("\\.");
            Path<Object> object_value = root.get(attributes[0]);
            for (int i = 1; i < attributes.length; i++) {
                object_value = object_value.get(attributes[i]);
            }
            Object valueStart = value.getLeft();
            Object valueEnd = value.getRight();
            if (valueStart == null || valueEnd == null || valueStart.getClass() != valueEnd.getClass()) {
                return null;
            }
            if (valueStart instanceof String && valueEnd instanceof String) {
                String stringStart = (String) valueStart;
                String stringEnd = (String) valueEnd;
                return builder.between(object_value.as(String.class), stringStart, stringEnd);
            } else if (valueStart instanceof Number && valueEnd instanceof Number) {
                Double numberStart = (Double) valueStart;
                Double numberEnd = (Double) valueEnd;
                return builder.between(object_value.as(Double.class), numberStart, numberEnd);
            } else if (valueStart instanceof Date && valueEnd instanceof Date) {
                Date dateStart = (Date) valueStart;
                Date dateEnd = (Date) valueEnd;
                return builder.between(object_value.as(Date.class), dateStart, dateEnd);
            }
            return null;
        };
    }

}
