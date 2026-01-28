package com.example.lojadevariedades.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.lojadevariedades.utils.ResponseJson;

@RestController
public class HomeController {

    @GetMapping("/")
    public ResponseEntity<ResponseJson> root() {
        return ResponseEntity.ok(ResponseJson.ok("ok", java.util.Map.of(
                "name", "Loja de Variedades"
        )));
    }
}
