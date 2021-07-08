package com.product.repository;

import com.product.repository.product.CustomProductRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.product.domain.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, CustomProductRepository {
    @Override
    List<Product> findAllById(Iterable<Long> longs);
}
