package com.example.demo.questions.boundary.dto;

import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder
public class QuestionDTO {
    private UUID id;

    private String contents;
    private List<AnswerDTO> answers;

    public UUID getId() {
        return id;
    }

    public List<AnswerDTO> getAnswers() {
        return answers;
    }

    public String getContents() {
        return contents;
    }
}