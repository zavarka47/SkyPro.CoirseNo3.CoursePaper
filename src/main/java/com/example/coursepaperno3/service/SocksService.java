package com.example.coursepaperno3.service;

import com.example.coursepaperno3.model.Color;
import com.example.coursepaperno3.model.Size;

import java.io.IOException;

public interface SocksService {
    //Post
    boolean addSocks(Color color, Size size, int cottonPart, int quantity) throws IOException;

    //Put
    boolean putSocks(Color color, Size size, int cottonPart, int quantity) throws IOException;

    //Get
    Integer getSocksWithCottonMoreThan(Color color, Size size, int cottonPart);

    Integer getSocksWithCottonLowThan(Color color, Size size, int cottonPart);

    //Delete
    boolean deleteSocks(Color color, Size size, int cottonPart, int quantity) throws IOException;
}
