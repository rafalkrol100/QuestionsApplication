package com.example.demo.questions.boundary;

import com.example.demo.questions.boundary.dto.CreateQuestionDTO;
import com.example.demo.questions.boundary.dto.QuestionDTO;
import com.example.demo.questions.control.QuestionService;
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

    @GetMapping
    public List<QuestionDTO> getAllQuestions() {
        return questionService.getAllQuestions();
    }

    @GetMapping()
    public List<QuestionDTO> getQuestionsByIds(@RequestParam List<UUID> ids) {
        return questionService.getQuestionsByIds(ids);
    }

    @PostMapping
    public void addQuestion(@Valid @NonNull @RequestBody CreateQuestionDTO question) {
        questionService.addQuestion(question);
    }

    @PutMapping()
    public void updateQuestion(@RequestParam UUID id,
                               @Valid @NonNull @RequestBody CreateQuestionDTO question) {
        questionService.updateQuestion(id, question);
    }

    @DeleteMapping()
    public void deleteQuestionByIds(@RequestParam List<UUID> ids) {
        questionService.deleteQuestion(ids);
    }
}