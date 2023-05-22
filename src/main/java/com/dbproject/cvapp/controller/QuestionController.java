package com.dbproject.cvapp.controller;

import com.dbproject.cvapp.model.Question;
import com.dbproject.cvapp.payload.response.MessageResponse;
import com.dbproject.cvapp.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/question")
@CrossOrigin(origins = "*", maxAge = 3600)
public class QuestionController {
    private final QuestionService questionService;

    @GetMapping("all")
    public List<Question> getAllQuestions() {
        return questionService.getAllQuestions();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("add")
    public ResponseEntity<?> createQuestion(@RequestBody Question question) {
        Question createdQuestion = questionService.createQuestion(question);
        return ResponseEntity.ok(new MessageResponse("Question created succesfully: " + createdQuestion));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("delete/{id}")
    public void deleteQuestion(@PathVariable Integer id){
        questionService.deleteQuestion(id);
    }
}
