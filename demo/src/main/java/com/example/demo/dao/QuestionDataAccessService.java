package com.example.demo.dao;

import com.example.demo.model.Question;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class QuestionDataAccessService implements QuestionDao{
    @Override
    public int insertQuestion(UUID id, Question question) {
        return 0;
    }

    @Override
    public List<Question> getAllQuestions() {
        return List.of();
    }

    @Override
    public Optional<Question> getQuestionById(UUID id) {
        return Optional.empty();
    }

    @Override
    public int deleteQuestionById(UUID id) {
        return 0;
    }

    @Override
    public int updateQuestionById(UUID id, Question question) {
        return 0;
    }
}
