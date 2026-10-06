package engine.businesslayer;

import engine.dto.CompletionsDTO;
import engine.dto.QuizDTO;
import engine.dto.SolutionDTO;
import engine.dto.SolutionResponseDTO;
import engine.persistence.QuizRepository;
import engine.persistence.CompletionsRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class QuizService {

    private final QuizRepository quizRepository;
    private final CompletionsRepository completionsRepository;

    public QuizService(QuizRepository quizRepository, CompletionsRepository completionsRepository) {
        this.quizRepository = quizRepository;
        this.completionsRepository = completionsRepository;
    }

    public Page<QuizDTO> getAllQuizzes(Pageable page) {
        Page<Quiz> quizzesPage = quizRepository.findAll(page);
        return quizzesPage.map(QuizService::mapToDto);
    }

    public Optional<QuizDTO> getQuizById(long id) {
        return this.quizRepository.findById(id)
                .map(QuizService::mapToDto);
    }

    public QuizDTO addQuiz(QuizDTO quizDTO) {
        List<String> options = quizDTO.getOptions() == null
                ? List.of()
                : Arrays.asList(quizDTO.getOptions());

        List<Integer> answers = quizDTO.getAnswer() == null
                ? List.of()
                : Arrays.stream(quizDTO.getAnswer()).boxed().toList();

        Quiz entity = new Quiz(
                quizDTO.getTitle(),
                quizDTO.getText(),
                options,
                quizDTO.getCreatedBy(),
                answers
        );

        Quiz saved = this.quizRepository.save(entity);
        return mapToDto(saved);
    }

    public void deleteQuizById(long id) {
        this.quizRepository.deleteById(id);
    }

    public Optional<SolutionResponseDTO> solveQuiz(long id, SolutionDTO solutionDTO) {
        return this.quizRepository.findById(id)
                .map(quiz -> {
                    if (isAnswerCorrect(quiz, solutionDTO)) {
                        this.completionsRepository.save(
                                new Completions(quiz, LocalDateTime.now(), solutionDTO.getCreatedBy()));
                        return new SolutionResponseDTO(true, "Congratulations, you're right!");
                    }
                    return new SolutionResponseDTO(false, "Wrong answer! Please, try again.");
                });
    }

    private static boolean isAnswerCorrect(Quiz quizEntity, SolutionDTO solutionDTO) {
        Set<Integer> correctAnswers = quizEntity.getAnswers() == null
                ? Set.of()
                : new HashSet<>(quizEntity.getAnswers());

        Set<Integer> userAnswers = (solutionDTO == null || solutionDTO.getAnswer() == null)
                ? Set.of()
                : Arrays.stream(solutionDTO.getAnswer()).boxed().collect(Collectors.toSet());

        return correctAnswers.equals(userAnswers);
    }

    static QuizDTO mapToDto(Quiz quiz) {
        return new QuizDTO(
                quiz.getId(),
                quiz.getTitle(),
                quiz.getText(),
                quiz.getOptions() == null ? new String[0] : quiz.getOptions().toArray(String[]::new),
                quiz.getCreatedBy(),
                quiz.getAnswers() == null ? new int[0] : quiz.getAnswers().stream().mapToInt(Integer::intValue).toArray()
        );
    }
}
