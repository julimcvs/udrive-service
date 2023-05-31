package com.si.udriveservice.repository;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.metamodel.SetAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;

public interface BaseSpecs<T> {

    @NonNull
    default Specification<T> buildSpecAnd(Specification<T> pSpec1) {
        return (root, query, criteriaBuilder) -> {
            Predicate lPredicate1 = pSpec1.toPredicate(root, query, criteriaBuilder);
            return criteriaBuilder.and(lPredicate1);
        };
    }

    @NonNull
    default Specification<T> buildSpecAnd(Specification<T> pSpec1, Specification<T> pSpec2) {
        return (root, query, criteriaBuilder) -> {
            Predicate lPredicate1 = pSpec1.toPredicate(root, query, criteriaBuilder);
            Predicate lPredicate2 = pSpec2.toPredicate(root, query, criteriaBuilder);
            return criteriaBuilder.and(lPredicate1, lPredicate2);
        };
    }

    @NonNull
    default Specification<T> buildSpecOr(Specification<T> pSpec1, Specification<T> pSpec2) {
        return (root, query, criteriaBuilder) -> {
            Predicate lPredicate1 = pSpec1.toPredicate(root, query, criteriaBuilder);
            Predicate lPredicate2 = pSpec2.toPredicate(root, query, criteriaBuilder);
            return criteriaBuilder.or(lPredicate1, lPredicate2);
        };
    }

    @NonNull
    default String concatLike(String field) {
        return String.join("", "%", field.toUpperCase().trim(), "%");
    }

    @NonNull
    default Specification<T> forEquals(SingularAttribute<T, String> attribute, String data) {
        return (root, query, cb) -> {
            if (StringUtils.isNotEmpty(data)) {
                return cb.equal(cb.upper(root.get(attribute)), data.toUpperCase());
            }
            return cb.and();
        };
    }

    @NonNull
    default <D> Specification<T> forEquals(SingularAttribute<T, D> attribute, D data) {
        return (root, query, cb) -> {
            if (data != null) {
                return cb.equal(root.get(attribute), data);
            }
            return cb.and();
        };
    }

    @NonNull
    default Specification<T> forEqualsBoolean(SingularAttribute<T, Boolean> attribute, Boolean bool) {
        return (root, query, cb) -> {
            if (BooleanUtils.isTrue(bool)) {
                return cb.equal(root.get(attribute), bool);
            }
            return cb.and();
        };
    }

    @NonNull
    default <D> Specification<T> forEqualsEnum(SingularAttribute<T, D> attribute, D campo) {
        return (root, query, cb) -> {
            if (campo != null) {
                return cb.equal(root.get(attribute), campo);
            }
            return cb.and();
        };
    }

    @NonNull
    default <E, A> Specification<T> forEqualsJoin(SingularAttribute<T, E> attribute, A data, SingularAttribute<E, A> attrib) {
        return (root, query, cb) -> {
            if (data instanceof String && StringUtils.isNotEmpty((String) data)) {
                return cb.equal(root.get(attribute).get(attrib), data);
            }
            if (data instanceof Long) {
                return cb.equal(root.get(attribute).get(attrib), data);
            }
            return cb.and();
        };
    }

    @NonNull
    default <E, A> Specification<T> forEqualsJoinList(SetAttribute<T, E> attribute, A data, SingularAttribute<E, A> attrib) {
        return (root, query, cb) -> {
            if (data instanceof String && StringUtils.isNotEmpty((String) data)) {
                return cb.equal(root.join(attribute).get(attrib), data);
            }
            if (data instanceof Long) {
                return cb.equal(root.join(attribute).get(attrib), data);
            }
            return cb.and();
        };
    }

    @NonNull
    default <E, A> Specification<T> forEqualsJoinSet(SetAttribute<T, E> attribute, A data, SingularAttribute<E, A> attrib) {
        return (root, query, cb) -> {
            if (data != null) {
                return cb.equal(root.join(attribute, JoinType.INNER).get(attrib), data);
            }
            return cb.and();
        };
    }

    @NonNull
    default <D> Specification<T> forEqualsNotNull(SingularAttribute<T, D> attribute) {
        return (root, query, cb) -> {
            return cb.isNotNull(root.get(attribute));
        };
    }

    @NonNull
    default <D> Specification<T> forEqualsNull(SingularAttribute<T, D> attribute) {
        return (root, query, cb) -> {
            return cb.isNull(root.get(attribute));
        };
    }

    @NonNull
    default <D> Specification<T> forEqualsStringJoin(SingularAttribute<T, D> attribute, String data, SingularAttribute<D, String> attributeSistema) {
        return (root, query, cb) -> {
            if (StringUtils.isNotEmpty(data)) {
                return cb.equal(cb.upper(root.get(attribute).get(attributeSistema)), data.toUpperCase());
            }
            return cb.and();
        };
    }

    @NonNull
    default Specification<T> forIn(SingularAttribute<T, Long> attribute, List<Long> data) {
        return (root, query, cb) -> {
            if (!CollectionUtils.isEmpty(data)) {
                return root.get(attribute).in(data);
            }
            return cb.and();
        };
    }

    @NonNull
    default <D> Specification<T> forInJoin(SingularAttribute<T, D> attribute, List<Long> data, SingularAttribute<D, Long> attrib) {
        return (root, query, cb) -> {
            Join<T, D> tbJoin = root.join(attribute, JoinType.INNER);
            if (!CollectionUtils.isEmpty(data)) {
                return tbJoin.get(attrib).in(data);
            }
            return cb.and();
        };
    }

    @NonNull
    default Specification<T> forLike(SingularAttribute<T, String> attribute, String data) {
        return (root, query, cb) -> {
            if (StringUtils.isNotEmpty(data)) {
                return cb.like(cb.upper(root.get(attribute)), concatLike(data));
            }
            return cb.and();
        };
    }

    @NonNull
    default <D> Specification<T> forLikeJoin(SingularAttribute<T, D> attribute, String data, SingularAttribute<D, String> attributeSistema) {
        return (root, query, cb) -> {
            if (StringUtils.isNotEmpty(data)) {
                return cb.like(cb.upper(root.get(attribute).get(attributeSistema)), concatLike(data));
            }
            return cb.and();
        };
    }

    @NonNull
    default <D> Specification<T> forNotEquals(SingularAttribute<T, D> attribute, D data) {
        return (root, query, cb) -> {
            if (data instanceof String && StringUtils.isNotEmpty((String) data)) {
                return cb.notEqual(cb.upper(root.get(attribute).as(String.class)), ((String) data).toUpperCase());
            }
            if (data != null) {
                return cb.notEqual(root.get(attribute), data);
            }
            return cb.and();
        };
    }

    @NonNull
    default <E, A> Specification<T> forNotEqualsJoin(SingularAttribute<T, E> attribute, A data, SingularAttribute<E, A> attrib) {
        return (root, query, cb) -> {
            if (data instanceof String && StringUtils.isNotEmpty((String) data)) {
                return cb.equal(root.get(attribute).get(attrib), data);
            }
            if (data instanceof Long) {
                return cb.equal(root.get(attribute).get(attrib), data);
            }
            return cb.and();
        };
    }

    @NonNull
    default Specification<T> greaterThan(SingularAttribute<T, LocalDateTime> attribute, LocalDateTime date) {
        return (root, query, cb) -> {
            if (date == null) {
                return cb.and();
            }
            return cb.greaterThanOrEqualTo(root.get(attribute), date);
        };
    }

    default Specification<T> inRange(SingularAttribute<T, LocalDateTime> attribute, LocalDateTime startDate, LocalDateTime endDate) {
        return (root, query, cb) -> {
            if (startDate == null || endDate == null) {
                return cb.and();
            }
            return cb.between(root.get(attribute), startDate, endDate);
        };
    }

    default Specification<T> lessThan(SingularAttribute<T, LocalDateTime> attribute, LocalDateTime date) {
        return (root, query, cb) -> {
            if (date == null) {
                return cb.and();
            }
            return cb.lessThanOrEqualTo(root.get(attribute), date);
        };
    }

    @NonNull
    default <E> Specification<T> orderByDesc(SingularAttribute<T, E> attribute) {
        return (root, query, cb) -> {
            query.orderBy(cb.asc(root.get(attribute)));
            return cb.and();
        };
    }
}