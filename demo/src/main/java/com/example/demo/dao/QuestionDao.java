package com.example.demo.dao;

import com.example.demo.model.Question;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface QuestionDao {

    int insertQuestion(UUID id, Question question);

    default int insertQuestion(Question question) {
        UUID id = UUID.randomUUID();
        return insertQuestion(id, question);
    }

    List<Question> getAllQuestions();

    Optional<Question> getQuestionById(UUID id);

    int deleteQuestionById(UUID id);

    int updateQuestionById(UUID id, Question question);
}
