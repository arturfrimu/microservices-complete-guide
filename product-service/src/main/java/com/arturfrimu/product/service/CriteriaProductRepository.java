package com.arturfrimu.product.service;

import com.arturfrimu.product.dto.filter.SearchProductCriteria;
import com.arturfrimu.product.dto.response.ProductInfoResponse;
import com.arturfrimu.product.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CriteriaProductRepository {

    private final EntityManager entityManager;

    public List<ProductInfoResponse> list(SearchProductCriteria productFilter) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> cq = cb.createQuery(Product.class);

        Root<Product> product = cq.from(Product.class);
        List<Predicate> predicates = new ArrayList<>();

        if (productFilter.customerId() != null) {
            predicates.add(cb.equal(product.get(Product.Fields.customerId), productFilter.customerId()));
        }

        cq.where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(cq)
                .getResultList()
                .stream()
                .map(ProductInfoResponse::valueOf)
                .toList();
    }
}