package com.example.lojadevariedades.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.lojadevariedades.model.Product;
import com.example.lojadevariedades.repository.ProductRepository;

@Service
public class ProductService {

    private final ProductRepository repository;
    private final BarcodeService barcodeService;

    public ProductService(ProductRepository repository, BarcodeService barcodeService) {
        this.repository = repository;
        this.barcodeService = barcodeService;
    }

    public List<Product> findAll() {
        return repository.findAll();
    }

    public Page<Product> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Optional<Product> findById(Long id) {
        return repository.findById(id);
    }

    public Product save(Product product) {
        if (product.getCode() == null || product.getCode().isEmpty()) {
            product.setCode(java.util.UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        }
        if (product.getBarcode() == null || product.getBarcode().isEmpty()) {
            product.setBarcode(product.getCode());
        }
        return repository.save(product);
    }

    public byte[] getBarcodeImage(Long id) throws Exception {
        Product product = repository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        String text = product.getBarcode() != null ? product.getBarcode() : product.getCode();
        if (text == null) {
            throw new RuntimeException("Product has no barcode or code");
        }
        return barcodeService.generateBarcode(text, 300, 150);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
