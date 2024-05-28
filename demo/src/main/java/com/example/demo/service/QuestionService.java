package com.example.demo.service;

import com.example.demo.dao.QuestionDao;
import com.example.demo.model.Answer;
import com.example.demo.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class QuestionService {

    private final QuestionDao questionDao;

    @Autowired
    public QuestionService(@Qualifier("postgres") QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    public int addQuestion(Question question) {
        UUID id = UUID.randomUUID();
        int ret = questionDao.insertQuestion(id, question);

        for (Answer answer : question.getAnswers()) {
            questionDao.insertAnswers(answer, id);
        }

        return ret;
    }

    public List<Question> getAllQuestions() {
        return questionDao.getAllQuestions();
    }

    public Optional<Question> getQuestionById(UUID id) {
        return questionDao.getQuestionById(id);
    }

    public int deleteQuestion(UUID id) {
        return questionDao.deleteQuestionById(id);
    }

    public int updateQuestion(UUID id, Question question) {
        return questionDao.updateQuestionById(id, question);
    }

}
