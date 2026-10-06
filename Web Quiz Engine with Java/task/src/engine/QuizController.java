package engine;

import engine.businesslayer.CompletionService;
import engine.businesslayer.QuizService;
import engine.dto.CompletionsDTO;
import engine.dto.QuizDTO;
import engine.dto.SolutionDTO;
import engine.dto.SolutionResponseDTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/quizzes")
class QuizController {

    private final QuizService quizService;
    private final CompletionService completionService;

    public QuizController(QuizService quizService, CompletionService completionService) {
        this.quizService = quizService;
        this.completionService = completionService;
    }

    @GetMapping
    public PagedModel<QuizDTO> getQuestions(
            @PageableDefault(size = 10, page = 0, sort = "id") Pageable page) {
        Page<QuizDTO> quizDTOS = quizService.getAllQuizzes(page);
        return new PagedModel<>(quizDTOS);
    }

    @GetMapping("{id}")
    public ResponseEntity<QuizDTO> getQuestion(@PathVariable long id) {
        var question = quizService.getQuizById(id);
        return ResponseEntity.of(question);
    }

    @PostMapping
    public ResponseEntity<QuizDTO> addQuestion(
            @AuthenticationPrincipal UserDetails user, @RequestBody @Valid QuizDTO quizDTO) {
        quizDTO.setCreatedBy(user.getUsername());
        QuizDTO savedQuizDTO = quizService.addQuiz(quizDTO);
        return ResponseEntity.ok(savedQuizDTO);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteQuiz(@AuthenticationPrincipal UserDetails user, @PathVariable long id) {
        Optional<QuizDTO> quiz = quizService.getQuizById(id);

        if (quiz.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        if (!user.getUsername().equals(quiz.get().getCreatedBy())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        quizService.deleteQuizById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("{id}/solve")
    public ResponseEntity<SolutionResponseDTO> solveQuiz(
            @PathVariable int id, @RequestBody SolutionDTO solutionDTO, @AuthenticationPrincipal UserDetails user) {
        solutionDTO.setCreatedBy(user.getUsername());
        var solutionResponse = quizService.solveQuiz(id, solutionDTO);
        return solutionResponse.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("completed")
    public PagedModel<CompletionsDTO> getCompletions(
            @PageableDefault(size = 10, page = 0, sort = "completedAt", direction = Sort.Direction.DESC) Pageable page,
            @AuthenticationPrincipal UserDetails user
    ) {
        Page<CompletionsDTO> completionDTOPage = completionService.getAllCompletions(page, user.getUsername());
        return new PagedModel<>(completionDTOPage);
    }
}
