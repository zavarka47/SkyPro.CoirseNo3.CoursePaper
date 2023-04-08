package com.example.coursepaperno3.service.Impl;

import com.example.coursepaperno3.service.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

 @Service
public class FileServiceImpl implements FileService {
    @Value("${prth.to.files}")
    String filePath;
    @Value("${name.to.socks.file}")
    String socksFile;
    @Value("${name.to.operationsHistory.file}")
     String operationsFile;
    @Override
    public boolean createSocksFile() throws IOException {
        Files.deleteIfExists(Path.of(filePath, socksFile));
        Files.createFile(Path.of(filePath, socksFile));
        return true;
    }

    @Override
    public boolean saveSocksFile(String json) throws IOException {
        createSocksFile();
        Files.writeString(Path.of(filePath, socksFile), json);
        return true;
    }

    @Override
    public String readSocksFile() throws IOException {
        return Files.readString(Path.of(filePath, socksFile));
    }

    @Override
    public File getSocksFile(){
        return new File(filePath + "/" + socksFile);
    }




     @Override
     public boolean createOperationsFile() throws IOException {
         Files.deleteIfExists(Path.of(filePath, operationsFile));
         Files.createFile(Path.of(filePath, operationsFile));
         return true;
     }

     @Override
     public boolean saveOperationsFile(String json) throws IOException {
         createOperationsFile();
         Files.writeString(Path.of(filePath, operationsFile), json);
         return true;
     }

     @Override
     public String readOperationsFile() throws IOException {
         return Files.readString(Path.of(filePath, operationsFile));
     }

     @Override
     public File getOperationsFile(){
         return new File(filePath + "/" + operationsFile);
     }
}

