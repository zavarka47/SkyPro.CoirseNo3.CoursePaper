package com.example.coursepaperno3.service.Impl;

import com.example.coursepaperno3.model.Color;
import com.example.coursepaperno3.model.CottonPart;
import com.example.coursepaperno3.model.Size;
import com.example.coursepaperno3.model.Sock;
import com.example.coursepaperno3.service.SocksService;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class SocksServiceImpl implements SocksService {

    private final Map <Sock, Integer> socks = new TreeMap<>();
    private final Map <LocalDate, HashMap<Sock, Integer>> deleteHistory = new TreeMap<>();



    //Post
    public void addSocks (Color color, Size size, CottonPart cottonPart, int quantity){
        Sock sockNew = new Sock(color, size, cottonPart);
        if (socks.containsKey(sockNew)){
            socks.put(sockNew, (socks.get(sockNew) + quantity));
        } else {
            socks.put(sockNew, quantity);
        }
    }

    //Put
    public boolean putSocks (Color color, Size size, CottonPart cottonPart, int quantity) {
        Sock sockPut = new Sock(color, size, cottonPart);
        if (!socks.containsKey(sockPut) || (socks.get(sockPut) < quantity)) {
            return false;
        } else {
            socks.put(sockPut, (socks.get(sockPut) - quantity));
            return true;
        }
    }

    //Get
    public Integer getSocks (Color color, Size size, CottonPart cottonPart){
        Sock sockGet = new Sock(color, size, cottonPart);
        return socks.getOrDefault(sockGet, -1);
    }
    //Delete
    public boolean deleteSocks (Color color, Size size, CottonPart cottonPart, int quantity){
        Sock sockDelete = new Sock(color, size, cottonPart);
        if (!socks.containsKey(sockDelete) || (socks.get(sockDelete) < quantity)) {
            return false;
        } else {
            socks.put(sockDelete, (socks.get(sockDelete) - quantity));
            HashMap <Sock, Integer> test = new HashMap<>();
            test.put(sockDelete, quantity);
            deleteHistory.put(LocalDate.now(), test);
            return true;
        }
    }
}
