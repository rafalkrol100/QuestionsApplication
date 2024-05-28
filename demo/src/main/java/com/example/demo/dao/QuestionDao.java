package com.example.demo.dao;

import com.example.demo.model.Answer;
import com.example.demo.model.Question;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface QuestionDao {

    int insertQuestion(UUID id, Question question);

    int insertAnswers(Answer answer, UUID questionId);

    List<Question> getAllQuestions();

    Optional<Question> getQuestionById(UUID id);

    int deleteQuestionById(UUID id);

    int updateQuestionById(UUID id, Question question);
}
