package com.retrospective.controller;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AddFeedbackRequest {

    private LocalDate retroDate;
    private String retroName;
    private String name;
    private String feedbackBody;
    private FeedbackType feedbackType ;


    public enum FeedbackType {
        POSITIVE,
        NEGATIVE,
        IDEA,
        PRAISE;
    }
}
