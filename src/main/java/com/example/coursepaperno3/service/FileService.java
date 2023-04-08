package com.example.coursepaperno3.service;

import java.io.File;
import java.io.IOException;

public interface FileService {


    boolean createSocksFile() throws IOException;

    boolean createOperationsFile() throws IOException;

    boolean saveSocksFile(String json) throws IOException;
    boolean saveOperationsFile(String json) throws IOException;

    String readSocksFile() throws IOException;

    String readOperationsFile() throws IOException;

    File getSocksFile();

    File getOperationsFile();
}
