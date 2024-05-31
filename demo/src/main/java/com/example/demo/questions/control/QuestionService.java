package com.example.demo.questions.control;

import com.example.demo.idgenerator.control.UUIDGenerator;
import com.example.demo.questions.boundary.dto.AnswerDTO;
import com.example.demo.questions.boundary.dto.CreateQuestionDTO;
import com.example.demo.questions.boundary.dto.QuestionDTO;
import com.example.demo.questions.control.model.Answer;
import com.example.demo.questions.control.model.Question;
import com.example.demo.questions.entity.dao.QuestionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    private final QuestionDao questionDao;

    @Autowired
    public QuestionService(@Qualifier("postgres") QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    public void addQuestion(CreateQuestionDTO createQuestionDTO) {
        UUID id = UUIDGenerator.generateType1UUID();
        Question question = mapToQuestionFromCreateDTO(id, createQuestionDTO);
        questionDao.insertQuestion(id, question);

        for (Answer answer : question.getAnswers()) {
            questionDao.insertAnswer(answer, id);
        }
    }

    public List<QuestionDTO> getAllQuestions() {
        List<Question> questionsFromDao = questionDao.getAllQuestions();
        return questionsFromDao.stream()
                .map(this::mapQuestionToDTO)
                .collect(Collectors.toList());
    }

    public List<QuestionDTO> getQuestionsByIds(List<UUID> ids) {
        List<Question> questionsFromDao = questionDao.getQuestionByIds(ids);
        return questionsFromDao.stream()
                .map(this::mapQuestionToDTO)
                .collect(Collectors.toList());
    }

    public void deleteQuestion(List<UUID> ids) {
        questionDao.deleteQuestionById(ids);
    }

    public void updateQuestion(UUID id, CreateQuestionDTO createQuestionDTO) {
        Question question = mapToQuestionFromCreateDTO(id, createQuestionDTO);
        questionDao.updateQuestionById(question);
    }

    private Question mapToQuestionFromCreateDTO(UUID id, CreateQuestionDTO createQuestionDTO) {
        return Question.builder()
                .id(id)
                .contents(createQuestionDTO.getContents())
                .answers(mapToAnswerFromDTO(createQuestionDTO.getAnswers()))
                .build();
    }

    private Question mapToQuestionFromDTO(QuestionDTO questionDTO) {
        return Question.builder()
                .id(questionDTO.getId())
                .contents(questionDTO.getContents())
                .answers(mapToAnswerFromDTO(questionDTO.getAnswers()))
                .build();
    }

    private List<Answer> mapToAnswerFromDTO(List<AnswerDTO> answers) {
        return answers.stream().map(answer -> Answer.builder()
                .contents(answer.getContents())
                .isCorrect(answer.isCorrect())
                .build()).collect(Collectors.toList());
    }

    private QuestionDTO mapQuestionToDTO(Question question) {
        return QuestionDTO.builder()
                .id(question.getId())
                .contents(question.getContents())
                .answers(mapAnswerToDTO(question.getAnswers()))
                .build();
    }

    private List<AnswerDTO> mapAnswerToDTO(List<Answer> answers) {
        return answers.stream().map(answer -> AnswerDTO.builder()
                .contents(answer.getContents())
                .isCorrect(answer.isCorrect())
                .build()).collect(Collectors.toList());
    }
}