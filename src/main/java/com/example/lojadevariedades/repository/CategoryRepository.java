package com.example.lojadevariedades.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.lojadevariedades.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
