package com.example.coursepaperno3.service.Impl;

import com.example.coursepaperno3.model.*;
import com.example.coursepaperno3.service.FileService;
import com.example.coursepaperno3.service.SocksService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

@Service
public class SocksServiceImpl implements SocksService {

    private HashMap<Sock, Integer> socks = new HashMap<>();
    private ArrayList <History> operationsHistory =  new ArrayList<>();
    private final FileService fileService;
    public SocksServiceImpl(FileService fileService) {
        this.fileService = fileService;
    }

    @PostConstruct
    private void readSocksFile() throws IOException {
        File file = fileService.getSocksFile();
        if (file.exists() && file.length() != 0) {
            readFromSocksFile();
        }
    }

        @PostConstruct
        private void readOperationsFile() throws IOException {
        File file = fileService.getOperationsFile();
        if (file.exists() && file.length() != 0) {
            readFromOperationsFile();
        }
    }
    //Post
    @Override
    public boolean addSocks(Color color, Size size, int cottonPart, int quantity) throws IOException {
        Sock sockNew = new Sock(color, size, cottonPart);
        if (quantity < 0) {
            return false;
        }
        if (socks.containsKey(sockNew)) {
            socks.put(sockNew, (socks.get(sockNew) + quantity));
            History newRecord = new History(Operation.ADD, LocalDate.now().toString(), sockNew, quantity);
            operationsHistory.add(newRecord);
            saveToFiles();
            return true;
        } else {
            socks.put(sockNew, quantity);
            History newRecord = new History(Operation.ADD, LocalDate.now().toString(), sockNew, quantity);
            operationsHistory.add(newRecord);
            saveToFiles();
            return true;
        }
    }

    //Put
    @Override
    public boolean putSocks(Color color, Size size, int cottonPart, int quantity) throws IOException {
        Sock sockPut = new Sock(color, size, cottonPart);
        if (!socks.containsKey(sockPut) || (socks.get(sockPut) < quantity)) {
            return false;
        } else {
            socks.put(sockPut, (socks.get(sockPut) - quantity));
            History newRecord = new History(Operation.EDIT, LocalDate.now().toString(), sockPut, quantity);
            operationsHistory.add(newRecord);
            saveToFiles();
            return true;
        }
    }

    //Get
    @Override
    public Integer getSocksWithCottonMoreThan(Color color, Size size, int cottonPart) {
        int count = 0;
        for (Map.Entry<Sock, Integer> foundSocks : socks.entrySet()) {
            if (foundSocks.getKey().getColor().equals(color) &&
                    foundSocks.getKey().getSize().equals(size) &&
                    foundSocks.getKey().getCottonPart() >= cottonPart) {
                count = count + foundSocks.getValue();
            }
        }
        return count;
    }

    @Override
    public Integer getSocksWithCottonLowThan(Color color, Size size, int cottonPart) {
        int count = 0;
        for (Map.Entry<Sock, Integer> foundSocks : socks.entrySet()) {
            if (foundSocks.getKey().getColor().equals(color) &&
                    foundSocks.getKey().getSize().equals(size) &&
                    foundSocks.getKey().getCottonPart() <= cottonPart) {
                count = count + foundSocks.getValue();
            }
        }
        return count;
    }

    //Delete
    @Override
    public boolean deleteSocks(Color color, Size size, int cottonPart, int quantity) throws IOException {
        Sock sockDelete = new Sock(color, size, cottonPart);
        if (!socks.containsKey(sockDelete) || socks.get(sockDelete) < quantity) {
            return false;
        } else {
            socks.put(sockDelete, (socks.get(sockDelete) - quantity));
            History newRecord = new History(Operation.EDIT, LocalDate.now().toString(), sockDelete, quantity);
            operationsHistory.add(newRecord);
            saveToFiles();
            return true;
        }
    }

    //Work With File

    private void saveToFiles() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        String operationsJson = mapper.writeValueAsString(operationsHistory);
        fileService.saveOperationsFile(operationsJson);

        ArrayList<SocksSupport> socksSupports = new ArrayList<>();
        for (Map.Entry<Sock,Integer> s:socks.entrySet()) {
            socksSupports.add(new SocksSupport(s.getValue(), s.getKey()));}

        String socksJson = mapper.writeValueAsString(socksSupports);
        fileService.saveSocksFile(socksJson);

    }

    private void readFromSocksFile() throws IOException {
        String json = fileService.readSocksFile();
        ArrayList<SocksSupport> socksSupports = new Gson().fromJson(json, ArrayList.class);

        for (SocksSupport s:socksSupports) {
            socks.put(s.getSock(), s.getQuantity());
        }


    }

    private void readFromOperationsFile() throws IOException {
        String json = fileService.readOperationsFile();
        operationsHistory = new ObjectMapper().readValue(json, new TypeReference<ArrayList<History>>() {
        });
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private class SocksSupport {
        int quantity;
        Sock sock;
    }

}

