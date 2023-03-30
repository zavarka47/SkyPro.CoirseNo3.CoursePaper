package com.example.coursepaperno3.controller;

import com.example.coursepaperno3.model.Color;
import com.example.coursepaperno3.model.CottonPart;
import com.example.coursepaperno3.model.Size;
import com.example.coursepaperno3.service.SocksService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/socks")
public class SocksController {
    private final SocksService socksService;

    public SocksController(SocksService socksService) {
        this.socksService = socksService;
    }

    @Operation(
            summary = "Приход товара на склад",
            description = "Введите парамметры носков входящей партии и их количество в партии")
    @Parameters (value = {
            @Parameter (name = "quantity", example = "40")
    })
    @PostMapping("/add")
    public ResponseEntity addSocks (Color color, Size size, CottonPart cottonPart, int quantity){
        boolean result = socksService.addSocks(color, size, cottonPart, quantity);
        if (result){
            return ResponseEntity.ok("Партия носков успешно добавлена на склад");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @Operation(
            summary = "Отгрузка товара со склада",
            description = "Введите парамметры носков отгружаемой партии и их количество в партии")
    @Parameters (value = {
            @Parameter (name = "quantity", example = "40")
    })
    @PutMapping("/edit")
    public ResponseEntity putSocks (Color color, Size size, CottonPart cottonPart, int quantity){
        boolean result = socksService.putSocks(color, size, cottonPart, quantity);
        if (result){
            return ResponseEntity.ok("Партия носков успешно отгружена со склада");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @Operation(
            summary = "Остаток товара на складе",
            description = "Введите парамметры носков остаток которых вы хотите узнать")
    @GetMapping("/get")
    public ResponseEntity getSocks (Color color, Size size, CottonPart cottonPart){
        int result = socksService.getSocks(color, size, cottonPart);
        if (result>=0){
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @Operation(
            summary = "Списание бракованного товара",
            description = "Введите парамметры и количество бракованных носков")
    @Parameters (value = {
            @Parameter (name = "quantity", example = "40")
    })
    @DeleteMapping("/remove")
    public ResponseEntity deleteSocks (Color color, Size size, CottonPart cottonPart, int quantity){
        boolean result = socksService.putSocks(color, size, cottonPart, quantity);
        if (result){
            return ResponseEntity.ok("Партия носков успешносписана");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
