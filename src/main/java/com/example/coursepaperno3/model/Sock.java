package com.example.coursepaperno3.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Sock {
    Color color;
    Size size;
    CottonPart cottonPart;

    public Sock(Color color, Size size, CottonPart cottonPart) {
        this.color = color;
        this.size = size;
        this.cottonPart = cottonPart;
    }
}
