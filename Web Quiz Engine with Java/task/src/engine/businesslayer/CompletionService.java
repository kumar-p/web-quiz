package engine.businesslayer;

import engine.dto.CompletionsDTO;
import engine.persistence.CompletionsRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CompletionService {
    private final CompletionsRepository completionsRepository;

    CompletionService(CompletionsRepository completionsRepository) {
        this.completionsRepository = completionsRepository;
    }

    public Page<CompletionsDTO> getAllCompletions(Pageable page, String username) {
        Page<Completions> completionsPage = completionsRepository.findAllByCreatedBy(username, page);
        return completionsPage.map(CompletionService::mapToDto);
    }

    private static CompletionsDTO mapToDto(Completions completions) {
        return new CompletionsDTO(
                completions.getQuiz().getId(),
                completions.getCompletedAt(),
                completions.getCreatedBy()
        );
    }
}
