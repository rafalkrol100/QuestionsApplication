package com.example.demo.api;

import com.example.demo.model.Question;
import com.example.demo.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RequestMapping("api/v1/question")
@RestController
public class QuestionController {

    private final QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping
    public void addQuestion(@Valid @NonNull @RequestBody Question question) {
        questionService.addQuestion(question);
    }

    @GetMapping
    public List<Question> getAllQuestions() {
        return questionService.getAllQuestions();
    }

    @GetMapping(path = "{id}")
    public Question getQuestionById(@PathVariable("id") UUID id) {
        return questionService.getQuestionById(id)
                .orElse(null);
    }

    @DeleteMapping(path = "{id}")
    public void deleteQuestionById(@PathVariable("id") UUID id) {
        questionService.deleteQuestion(id);
    }

    @PutMapping(path = "{id}")
    public void updateQuestion(@PathVariable("id") UUID id,
                               @Valid @NonNull @RequestBody Question question) {
        questionService.updateQuestion(id, question);
    }
}
