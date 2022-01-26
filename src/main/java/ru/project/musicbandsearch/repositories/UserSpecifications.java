package ru.project.musicbandsearch.repositories;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.MultiValueMap;
import ru.project.musicbandsearch.entities.User;

public class UserSpecifications {
    private static Specification<User> priceGreaterOrEqualsThan(int minId) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("min_user_id"), minId);
    }

    private static Specification<User> priceLesserOrEqualsThan(int maxId) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("max_user_id"), maxId);
    }

    private static Specification<User> titleLike(String titlePart) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("name"), String.format("%%%s%%", titlePart));
    }

    public static Specification<User> build(MultiValueMap<String, String> params) {
        Specification<User> spec = Specification.where(null);
        if (params.containsKey("min_user_id") && !params.getFirst("min_user_id").isBlank()) {
            spec = spec.and(UserSpecifications.priceGreaterOrEqualsThan(Integer.parseInt(params.getFirst("min_price"))));
        }
        if (params.containsKey("max_user_id") && !params.getFirst("max_user_id").isBlank()) {
            spec = spec.and(UserSpecifications.priceLesserOrEqualsThan(Integer.parseInt(params.getFirst("max_price"))));
        }
        if (params.containsKey("name") && !params.getFirst("name").isBlank()) {
            spec = spec.and(UserSpecifications.titleLike(params.getFirst("name")));
        }
        return spec;
    }
}