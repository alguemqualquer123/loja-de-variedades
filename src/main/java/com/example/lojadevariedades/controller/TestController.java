package com.example.lojadevariedades.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.lojadevariedades.utils.LoadJson;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/loadjson")
    public ResponseEntity<String> loadJson(@RequestParam String file) {
        String content = LoadJson.loadJson(file);
        return ResponseEntity.ok(content);
    }
}
