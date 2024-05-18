package com.retrospective.controller;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;
@Data
public class CreateRetroRequest {

    private String name;
    private String summary;
    private LocalDate date;
    private List<String> participants;
}
