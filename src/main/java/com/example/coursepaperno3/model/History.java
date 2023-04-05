package com.example.coursepaperno3.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class History {
    Operation operation;
    String localDate;
    Sock sock;
    int quantity;
}
