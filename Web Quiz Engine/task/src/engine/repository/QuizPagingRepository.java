package engine.repository;

import engine.model.QuizEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizPagingRepository extends PagingAndSortingRepository<QuizEntity, Integer> { }
