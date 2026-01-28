package com.example.lojadevariedades.config;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.lojadevariedades.model.Product;
import com.example.lojadevariedades.repository.ProductRepository;

@Configuration
public class CreateDefaultItems {
    private static final Logger logger = LoggerFactory.getLogger(CreateDefaultItems.class);

        private final Map<String, Double> defaultItems = Map.ofEntries(
            Map.entry("Caneta", 2.50),
            Map.entry("Caderno", 15.00),
            Map.entry("Mochila", 120.00),
            Map.entry("Lápis", 1.20),
            Map.entry("Borracha", 0.80),
            Map.entry("Apontador", 3.00),
            Map.entry("Régua", 4.50),
            Map.entry("Tesoura", 6.50),
            Map.entry("Cola", 4.00),
            Map.entry("Estojo", 18.00),
            Map.entry("Marcador", 2.80),
            Map.entry("Post-it", 5.00),
            Map.entry("Grampeador", 25.00),
            Map.entry("Calculadora", 85.00),
            Map.entry("Agenda", 30.00),
            Map.entry("Pasta", 12.00),
            Map.entry("Clip", 1.50),
            Map.entry("Caderno de desenho", 22.00),
            Map.entry("Mouse", 75.00),
            Map.entry("Teclado", 120.00)
        );

    @Bean("createDefaultItemsRunner")
    CommandLineRunner createDefaultItemsRunner(ProductRepository productRepository) {
        return args -> {
            AtomicInteger seq = new AtomicInteger(1000);
            for (Map.Entry<String, Double> entry : defaultItems.entrySet()) {
                String name = entry.getKey();
                if (productRepository.findByName(name).isEmpty()) {
                    Product product = new Product();
                    product.setName(name);
                    product.setPrice(BigDecimal.valueOf(entry.getValue()));
                    product.setStock(10);
                    String code = String.valueOf(seq.getAndIncrement());
                    product.setCode(code);
                    product.setBarcode("BR-" + code);
                    productRepository.save(product);
                    logger.info("Created default product: {} (code={}, barcode={})", name, code, product.getBarcode());
                } else {
                    logger.debug("Product {} already exists, skipping", name);
                }
            }
        };
    }
}
