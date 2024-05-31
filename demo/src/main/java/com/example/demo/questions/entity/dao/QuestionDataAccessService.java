package com.example.demo.questions.entity.dao;

import com.example.demo.questions.control.model.Answer;
import com.example.demo.questions.control.model.Question;
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
    public void insertQuestion(UUID id, Question question) {
        String sql = "INSERT INTO question (questionId, contents) VALUES (?, ?)";
        jdbcTemplate.update(sql, id, question.getContents());
    }

    @Override
    public void insertAnswer(Answer answer, UUID questionId) {
        String sql = "INSERT INTO answer (contents, isCorrect, questionId) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, answer.getContents(), answer.isCorrect(), questionId);
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
    public List<Question> getQuestionByIds(List<UUID> ids) {
        List<Question> questions = new ArrayList<>();
        final String sql = "SELECT questionId, contents FROM question WHERE id = ?";

        for (UUID id : ids) {
            Question question = jdbcTemplate.queryForObject(sql, new Object[]{id}, mapQuestionFromDb());
            questions.add(question);
        }

        return questions;
    }

    @Override
    public void deleteQuestionById(List<UUID> ids) {
        final String sql = "DELETE FROM question WHERE questionId = ?";
        for (UUID id : ids) {
            deleteAnswersByQuestionId(id);
            jdbcTemplate.update(sql, id);
        }
    }

    @Override
    public void updateQuestionById(Question question) {
        UUID questionId = question.getId();
        deleteAnswersByQuestionId(questionId);
        for (Answer answer : question.getAnswers()) {
            insertAnswer(answer, questionId);
        }

        final String sql = "UPDATE question SET questionId = ?, contents = ? WHERE questionId = ?";
        jdbcTemplate.update(sql, question.getId(), question.getContents(), question.getId());
    }

    private void deleteAnswersByQuestionId(UUID questionId) {
        final String sql = "DELETE FROM answer WHERE questionId = ?";
        jdbcTemplate.update(sql, questionId);
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