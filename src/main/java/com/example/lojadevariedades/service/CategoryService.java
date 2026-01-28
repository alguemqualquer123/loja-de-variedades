package com.example.lojadevariedades.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.lojadevariedades.model.Category;
import com.example.lojadevariedades.repository.CategoryRepository;

@Service
public class CategoryService {

    private final CategoryRepository repository;

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    public List<Category> findAll() {
        return repository.findAll();
    }

    public Optional<Category> findById(Long id) {
        return repository.findById(id);
    }

    public Category save(Category category) {
        return repository.save(category);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
