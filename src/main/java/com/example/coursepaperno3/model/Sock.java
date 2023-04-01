package com.example.coursepaperno3.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Sock {
    Color color;
    Size size;
    int cottonPart;

    public Sock(Color color, Size size, int cottonPart) {
        this.color = color;
        this.size = size;
        this.cottonPart = cottonPart;
    }
}
