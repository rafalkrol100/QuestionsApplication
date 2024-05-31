package com.example.demo.questions.boundary;

import com.example.demo.questions.boundary.dto.CreateQuestionDTO;
import com.example.demo.questions.boundary.dto.QuestionDTO;
import com.example.demo.questions.control.QuestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Get all questions")
    @GetMapping(path = "all")
    public List<QuestionDTO> getAllQuestions() {
        return questionService.getAllQuestions();
    }

    @Operation(summary = "Get questions by ids")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Question not found",
                    content = @Content)
    })
    @GetMapping
    public List<QuestionDTO> getQuestionsByIds(@Parameter(description = "List of question ids to be searched")
                                               @RequestParam List<UUID> id) {
        return questionService.getQuestionsByIds(id);
    }

    @Operation(summary = "Add question")
    @PostMapping
    public void addQuestion(@Parameter(description = "CreateQuestionDTO")
                                @Valid @NonNull @RequestBody CreateQuestionDTO question) {
        questionService.addQuestion(question);
    }

    @Operation(summary = "Update question")
    @PutMapping()
    public void updateQuestion(@Parameter(description = "Id of a question to be updated") @RequestParam UUID id,
                               @Valid @NonNull @RequestBody CreateQuestionDTO question) {
        questionService.updateQuestion(id, question);
    }

    @Operation(summary = "Delete questions by ids")
    @DeleteMapping()
    public void deleteQuestionByIds(@Parameter(description = "Ids of questions to be deleted")
                                        @RequestParam List<UUID> id) {
        questionService.deleteQuestion(id);
    }
}