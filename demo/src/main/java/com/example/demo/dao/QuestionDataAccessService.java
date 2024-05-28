package com.example.demo.dao;

import com.example.demo.model.Answer;
import com.example.demo.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository("postgres")
public class QuestionDataAccessService implements QuestionDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public QuestionDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int insertQuestion(UUID id, Question question) {
        String sql = "INSERT INTO question (questionId, contents) VALUES (?, ?)";
        return jdbcTemplate.update(sql, id, question.getContents());
    }

    @Override
    public int insertAnswers(Answer answer, UUID questionId) {
        String sql = "INSERT INTO answer (contents, isCorrect, questionId) VALUES (?, ?, ?)";
        return jdbcTemplate.update(sql, answer.getContents(), answer.isCorrect(), questionId);
    }

    @Override
    public List<Question> getAllQuestions() {
        return selectAllQuestions().stream()
                .map(question -> {
                    List<Answer> answers = selectAllAnswers(question.getId());
                    return new Question(question.getId(), question.getContents(), answers);
                })
                .toList();
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

    private List<Question> selectAllQuestions() {
        final String sql = "SELECT questionId, contents FROM question";
        return jdbcTemplate.query(sql, mapQuestionFromDb());
    }

    private List<Answer> selectAllAnswers(UUID id) {
        final String sql = "SELECT answer.contents, answer.isCorrect FROM question JOIN answer USING (questionId) WHERE question.questionId = ?";
        return jdbcTemplate.query(
                sql,
                new Object[]{id},
                mapAnswerFromDb()
        );
    }

    private RowMapper<Answer> mapAnswerFromDb() {
        return (resultSet, i) -> {
            String contents = resultSet.getString("contents");
            boolean isCorrect = resultSet.getBoolean("isCorrect");

            return new Answer(contents, isCorrect);
        };
    }

    private RowMapper<Question> mapQuestionFromDb() {
        return (resultSet, i) -> {
            UUID id = UUID.fromString(resultSet.getString("questionId"));
            String contents = resultSet.getString("contents");

            return new Question(id, contents, Collections.emptyList());
        };
    }
}

