package com.example.coursepaperno3.controller;

import com.example.coursepaperno3.model.Color;
import com.example.coursepaperno3.model.Size;
import com.example.coursepaperno3.service.SocksService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
            summary = "Остаток товара на складе (фильтр - собержание хлопка больше или равно)",
            description = "Введите парамметры носков остаток которых вы хотите узнать")
    @Parameter (name = "cottonPert", example = "55")
    @ApiResponses (value = {
            @ApiResponse (responseCode = "200", description = "Запрос выполнен"),
            @ApiResponse (responseCode = "400", description = "Товара нет на складе в нужном количестве или параметры запроса имеют некорректный формат"),
            @ApiResponse (responseCode = "500", description = "Произошла ошибка, не зависящая от вызывающей стороны")
    })
    @GetMapping("/getMoreThen")
    public ResponseEntity getSocksWithCottonMoreThan (Color color, Size size, int cottonPart){
        int result = socksService.getSocksWithCottonMoreThan(color, size, cottonPart);
        if (result>0){
            return ResponseEntity.ok(result);
        } else {
            return new ResponseEntity<>("Носков с запрашиваемыми параметрами нет на складе", HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(
            summary = "Остаток товара на складе (фильтр - собержание хлопка меньше или равно)",
            description = "Введите парамметры носков остаток которых вы хотите узнать")
    @Parameter (name = "cottonPert", example = "45")
    @ApiResponses (value = {
            @ApiResponse (responseCode = "200", description = "Запрос выполнен"),
            @ApiResponse (responseCode = "400", description = "Товара нет на складе в нужном количестве или параметры запроса имеют некорректный формат"),
            @ApiResponse (responseCode = "500", description = "Произошла ошибка, не зависящая от вызывающей стороны")
    })
    @GetMapping("/getLowThen")
    public ResponseEntity getSocksWithCottonLowThan (Color color, Size size, int cottonPart){
        int result = socksService.getSocksWithCottonLowThan(color, size, cottonPart);
        if (result>0){
            return ResponseEntity.ok(result);
        } else {
            return new ResponseEntity<>("Носков с запрашиваемыми параметрами нет на складе", HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(
            summary = "Приход товара на склад",
            description = "Введите парамметры носков входящей партии и их количество в партии")
    @Parameters (value = {
            @Parameter (name = "quantity", example = "40")
    })
    @ApiResponses (value = {
            @ApiResponse (responseCode = "200", description = "Удалось добавить приход"),
            @ApiResponse (responseCode = "400", description = "Параметры запроса отсутствуют или имеют некорректный формат"),
            @ApiResponse (responseCode = "500", description = "Произошла ошибка, не зависящая от вызывающей стороны")
    })
    @PostMapping("/add")
    public ResponseEntity addSocks (Color color, Size size, int cottonPart, int quantity){
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
    @Parameter (name = "quantity", example = "40")
    @ApiResponses (value = {
            @ApiResponse (responseCode = "200", description = "Удалось произвести отпуск носков со склада"),
            @ApiResponse (responseCode = "400", description = "Товара нет на складе в нужном количестве или параметры запроса имеют некорректный формат"),
            @ApiResponse (responseCode = "500", description = "Произошла ошибка, не зависящая от вызывающей стороны")
    })
    @PutMapping("/edit")
    public ResponseEntity putSocks (Color color, Size size, int cottonPart, int quantity){
        boolean result = socksService.putSocks(color, size, cottonPart, quantity);
        if (result){
            return ResponseEntity.ok("Партия носков успешно отгружена со склада");
        } else {
            return new ResponseEntity("Что-то пошло не так, проверь параметры запроса", HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(
            summary = "Списание бракованного товара",
            description = "Введите парамметры и количество бракованных носков")
    @Parameter (name = "quantity", example = "40")
    @ApiResponses (value = {
            @ApiResponse (responseCode = "200", description = "Запрос выполнен, товар списан со склада"),
            @ApiResponse (responseCode = "400", description = "Параметры запроса отсутствуют или имеют некорректный формат"),
            @ApiResponse (responseCode = "500", description = "Произошла ошибка, не зависящая от вызывающей стороны")
    })
    @DeleteMapping("/remove")
    public ResponseEntity deleteSocks (Color color, Size size, int cottonPart, int quantity){
        int result = socksService.deleteSocks(color, size, cottonPart, quantity);
        if (result > 0){
            return ResponseEntity.ok("Партия носков успешно списана");
        }
        if (result == 0){
            return new ResponseEntity("Вы пытаетесь списать товар, в количестве больше чем есть на складе",
                     HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity("Вы пытаетесь списать товар, которого нет на складе",
                    HttpStatus.BAD_REQUEST);
        }
    }
}
