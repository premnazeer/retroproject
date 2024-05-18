package com.retrospective.domain;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class Retrospective {

    private String name;
    private String summary;
    private LocalDate date;
    private List<String> participants;
    private List<RetroFeedback> feedback;
}
