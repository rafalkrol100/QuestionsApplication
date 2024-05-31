package com.example.demo.questions.boundary.dto;

import lombok.Builder;

@Builder
public class AnswerDTO {
    private String contents;
    private boolean isCorrect;

    public String getContents() {
        return contents;
    }

    public boolean isCorrect() {
        return isCorrect;
    }
}