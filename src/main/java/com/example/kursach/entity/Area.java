package com.example.kursach.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Area {
    private Long id;
    private String name;
    private String code;
    private String flag;
}
