package com.example.lojadevariedades.repository;

import com.example.lojadevariedades.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	java.util.Optional<Product> findByName(String name);
}
