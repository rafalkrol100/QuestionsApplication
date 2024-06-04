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
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<QuestionDTO>> getAllQuestions() {
        List<QuestionDTO> questions = questionService.getAllQuestions();
        return new ResponseEntity<List<QuestionDTO>> (questions, HttpStatus.OK);
    }

    @Operation(summary = "Get questions by ids")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Question not found",
                    content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<QuestionDTO>> getQuestionsByIds(@Parameter(description = "List of question ids to be searched")
                                               @RequestParam List<UUID> id) {
        try {
            List<QuestionDTO> questions = questionService.getQuestionsByIds(id);
            return new ResponseEntity<List<QuestionDTO>> (questions, HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<> (HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Add question")
    @PostMapping
    public ResponseEntity<Void> addQuestion(@Parameter(description = "CreateQuestionDTO")
                                @Valid @NonNull @RequestBody CreateQuestionDTO question) {
        questionService.addQuestion(question);
        return  new ResponseEntity<> (HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Update question")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Question not found",
                    content = @Content)
    })
    @PutMapping()
    public ResponseEntity<Void> updateQuestion(@Parameter(description = "Id of a question to be updated") @RequestParam UUID id,
                               @Valid @NonNull @RequestBody CreateQuestionDTO question) {
        try {
            questionService.updateQuestion(id, question);
            return  new ResponseEntity<> (HttpStatus.NO_CONTENT);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<> (HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Delete questions by ids")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Question not found",
                    content = @Content)
    })
    @DeleteMapping()
    public ResponseEntity<Void> deleteQuestionByIds(@Parameter(description = "Ids of questions to be deleted")
                                        @RequestParam List<UUID> id) {
        try {
            questionService.deleteQuestion(id);
            return  new ResponseEntity<> (HttpStatus.NO_CONTENT);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<> (HttpStatus.NOT_FOUND);
        }
    }
}