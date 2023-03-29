package com.example.coursepaperno3.service;

import com.example.coursepaperno3.model.Color;
import com.example.coursepaperno3.model.CottonPart;
import com.example.coursepaperno3.model.Size;

public interface SocksService {
    //Post
    boolean addSocks(Color color, Size size, CottonPart cottonPart, int quantity);

    //Put
    boolean putSocks(Color color, Size size, CottonPart cottonPart, int quantity);

    //Get
    Integer getSocks(Color color, Size size, CottonPart cottonPart);

    //Delete
    boolean deleteSocks(Color color, Size size, CottonPart cottonPart, int quantity);
}
