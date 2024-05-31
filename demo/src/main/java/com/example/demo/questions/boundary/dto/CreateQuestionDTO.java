package com.example.demo.questions.boundary.dto;

import java.util.List;

public class CreateQuestionDTO {
    private String contents;
    private List<AnswerDTO> answers;

    public List<AnswerDTO> getAnswers() {
        return answers;
    }

    public String getContents() {
        return contents;
    }
}
