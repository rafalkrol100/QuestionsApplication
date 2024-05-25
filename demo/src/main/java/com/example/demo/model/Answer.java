package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class Answer {
    private final String contents;
    private final boolean isCorrect;

    public Answer(@JsonProperty("contents") String contents,
                  @JsonProperty("isCorrect") boolean isCorrect) {
        this.contents = contents;
        this.isCorrect = isCorrect;
    }

    public String getContents() {
        return contents;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Answer answer = (Answer) o;
        return isCorrect == answer.isCorrect && Objects.equals(contents, answer.contents);
    }

    @Override
    public int hashCode() {
        return Objects.hash(contents, isCorrect);
    }
}
