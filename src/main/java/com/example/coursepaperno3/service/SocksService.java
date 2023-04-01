package com.example.coursepaperno3.service;

import com.example.coursepaperno3.model.Color;
import com.example.coursepaperno3.model.Size;

public interface SocksService {
    //Post
    boolean addSocks(Color color, Size size, int cottonPart, int quantity);

    //Put
    boolean putSocks(Color color, Size size, int cottonPart, int quantity);

    //Get
    Integer getSocksWithCottonMoreThan(Color color, Size size, int cottonPart);

    Integer getSocksWithCottonLowThan(Color color, Size size, int cottonPart);

    //Delete
    int deleteSocks(Color color, Size size, int cottonPart, int quantity);
}
