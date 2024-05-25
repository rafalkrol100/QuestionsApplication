package com.example.demo.dao;

import com.example.demo.model.Question;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("fakeDao")
public class FakeQuestionDataAccessService implements QuestionDao {

    private static List<Question> DB = new ArrayList<>();

    @Override
    public int insertQuestion(UUID id, Question question) {
        DB.add(new Question(id, question.getContents(), question.getAnswers()));
        return 1;
    }

    @Override
    public int insertQuestion(Question question) {
        return QuestionDao.super.insertQuestion(question);
    }

    @Override
    public List<Question> getAllQuestions() {
        return DB;
    }

    @Override
    public Optional<Question> getQuestionById(UUID id) {
        return DB.stream()
                .filter(question -> question.getId().equals(id))
                .findFirst();
    }

    @Override
    public int deleteQuestionById(UUID id) {
        Optional<Question> questionOptional = getQuestionById(id);
        if (questionOptional.isEmpty()) {
            return 0;
        }
        DB.remove(questionOptional.get());
        return 1;
    }

    @Override
    public int updateQuestionById(UUID id, Question question) {
        return getQuestionById(id)
                .map(q -> {
                    int indexOfQuestionToDelete = DB.indexOf(q);
                    if (indexOfQuestionToDelete >= 0) {
                        DB.set(indexOfQuestionToDelete, question);
                        return 1;
                    }
                    return 0;
                })
                .orElse(0);
    }
}
