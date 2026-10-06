package engine.persistence;

import engine.businesslayer.Quiz;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizRepository extends ListCrudRepository<Quiz, Long>, PagingAndSortingRepository<Quiz, Long> {
}
