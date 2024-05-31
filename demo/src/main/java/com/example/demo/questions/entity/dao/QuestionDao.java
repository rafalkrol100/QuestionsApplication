package com.example.demo.questions.entity.dao;

import com.example.demo.questions.control.model.Answer;
import com.example.demo.questions.control.model.Question;

import java.util.List;
import java.util.UUID;

public interface QuestionDao {

    void insertQuestion(UUID id, Question question);

    void insertAnswer(Answer answer, UUID questionId);

    List<Question> getAllQuestions();

    List<Question> getQuestionByIds(List<UUID> ids);

    void deleteQuestionById(List<UUID> ids);

    void updateQuestionById(Question question);
}
