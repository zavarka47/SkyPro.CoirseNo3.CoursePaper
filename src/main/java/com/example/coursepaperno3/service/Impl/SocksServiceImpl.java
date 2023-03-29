package com.example.coursepaperno3.service.Impl;

import com.example.coursepaperno3.model.Color;
import com.example.coursepaperno3.model.CottonPart;
import com.example.coursepaperno3.model.Size;
import com.example.coursepaperno3.model.Sock;
import com.example.coursepaperno3.service.SocksService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class SocksServiceImpl implements SocksService {

    //private final Map<Sock, Integer> socks = new TreeMap<>();
    //private final List <Sock> socks = new ArrayList<>();
    private final Map <Sock, Integer> socks = new HashMap<>();
    private final Map<LocalDate, HashMap<Sock, Integer>> deleteHistory = new TreeMap<>();

    //Post
    @Override
    public boolean addSocks(Color color, Size size, CottonPart cottonPart, int quantity) {
        Sock sockNew = new Sock(color, size, cottonPart);
        if (quantity<0){return false;}
        if (socks.containsKey(sockNew)) {
            socks.put(sockNew, (socks.get(sockNew) + quantity));
            return true;
        } else {
            socks.put(sockNew, quantity);
            return true;
        }
    }

    //Put
    @Override
    public boolean putSocks(Color color, Size size, CottonPart cottonPart, int quantity) {
        Sock sockPut = new Sock(color, size, cottonPart);
        if (!socks.containsKey(sockPut) || (socks.get(sockPut) < quantity)) {
            return false;
        } else {
            socks.put(sockPut, (socks.get(sockPut) - quantity));
            return true;
        }
    }

    //Get
    @Override
    public Integer getSocks(Color color, Size size, CottonPart cottonPart) {
        Sock sockGet = new Sock(color, size, cottonPart);
        return socks.getOrDefault(sockGet, -1);
    }

    //Delete
    @Override
    public boolean deleteSocks(Color color, Size size, CottonPart cottonPart, int quantity) {
        Sock sockDelete = new Sock(color, size, cottonPart);
        if (!socks.containsKey(sockDelete) || (socks.get(sockDelete) < quantity)) {
            return false;
        } else {
            socks.put(sockDelete, (socks.get(sockDelete) - quantity));
            if (deleteHistory.containsKey(LocalDate.now())) {
                deleteHistory.get(LocalDate.now()).put(sockDelete, quantity);
            } else {
                HashMap<Sock, Integer> socksDelete = new HashMap<>();
                socksDelete.put(sockDelete, quantity);
                deleteHistory.put(LocalDate.now(), socksDelete);
            }
            return true;
        }


    }
}
