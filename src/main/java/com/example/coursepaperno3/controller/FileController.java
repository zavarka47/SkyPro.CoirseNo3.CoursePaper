package com.example.coursepaperno3.controller;

import com.example.coursepaperno3.service.FileService;
import io.swagger.v3.oas.annotations.Operation;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@RestController
@RequestMapping("/report")
public class FileController {

    FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }
    @Operation (summary = "Скачать отчет \"История операций\"",
            description = "Скачать отчет, в котором отражена информация о движении товара (приемка, выдача, спасания) на скдладе")
    @GetMapping(value = "/exportOperationsHistory", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<InputStreamResource> downloadOperationsHistoryFile () throws FileNotFoundException {
        File operationsHistoryFile = fileService.getOperationsFile();
        if (operationsHistoryFile.exists()) {
            InputStreamResource resource = new InputStreamResource(new FileInputStream(operationsHistoryFile));
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .contentLength(operationsHistoryFile.length())
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"Operations.json\"")
                    .body(resource);
        } else {
            return ResponseEntity.noContent().build();
        }
    }


    @Operation (summary = "Скачать отчет \"Остаток товара на складе\"",
            description = "Скачать отчет, в котором отражена информация о товаре, имеющийся в наличии на складе")
    @GetMapping(value = "/exportSocks", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<InputStreamResource> downloadSocksFile () throws IOException {
        File socksFile = fileService.getSocksFile();
        if (socksFile.exists()){
            InputStreamResource resource = new InputStreamResource(new FileInputStream(socksFile));
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .contentLength(socksFile.length())
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"Socks.json\"")
                    .body(resource);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @Operation (summary = "Загрузить отчет \"Остаток товара на складе\"",
            description = "Загрузить отчет, в котором отражена информация о товаре, имеющийся в наличии на складе")
    @PostMapping(value = "/importSocks", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> upLoadSocksFile (@RequestParam MultipartFile socksFile) throws IOException {
        fileService.createSocksFile();
        File file = fileService.getSocksFile();
        try (FileOutputStream fos = new FileOutputStream(file)){
            IOUtils.copy(socksFile.getInputStream(), fos);
            return ResponseEntity.ok().build();
        } catch (IOException e){
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }


    @Operation (summary = "Загрузить отчет \"История операций\"",
            description = "Загрузить отчет, в котором отражена информация о движении товара (приемка, выдача, спасания) на скдладе")
    @PostMapping(value = "/importOperations", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> upLoadOperationsHistoryFile (@RequestParam MultipartFile operationsHistoryFile) throws IOException {
        fileService.createOperationsFile();
        File file = fileService.getOperationsFile();
        try (FileOutputStream fos = new FileOutputStream(file)){
            IOUtils.copy(operationsHistoryFile.getInputStream(), fos);
            return ResponseEntity.ok().build();
        } catch (IOException e){
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
