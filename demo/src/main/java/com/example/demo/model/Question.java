package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.*;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Question {
    private final UUID id;

    @NotBlank
    private final String contents;
    private final List<Answer> answers;

    public Question(@JsonProperty("id") UUID id,
                    @JsonProperty("contents") String contents,
                    @JsonProperty("answers") List<Answer> answers) {
        this.id = id;
        this.contents = contents;
        this.answers = answers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question = (Question) o;
        return Objects.equals(id, question.id) && Objects.equals(contents, question.contents) && Objects.equals(answers, question.answers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, contents, answers);
    }

    public UUID getId() {
        return id;
    }

    public String getContents() {
        return contents;
    }

    public List<Answer> getAnswers() {
        return answers;
    }
}
