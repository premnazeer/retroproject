package com.retrospective.domain;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class RetroFeedback {

        private String name;
        private String feedbackBody;
        private String feedbackType;

}
