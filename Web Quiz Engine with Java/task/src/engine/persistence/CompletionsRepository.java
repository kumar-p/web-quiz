package engine.persistence;

import engine.businesslayer.Completions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompletionsRepository extends
        ListCrudRepository<Completions, Long>, PagingAndSortingRepository<Completions, Long> {
    Page<Completions> findAllByCreatedBy(String createdBy, Pageable pageable);
}
