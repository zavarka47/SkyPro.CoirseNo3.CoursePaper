package com.example.coursepaperno3.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class History {
    private Operation operation;
    private String localDate;
    private Sock sock;
    private int quantity;
}
