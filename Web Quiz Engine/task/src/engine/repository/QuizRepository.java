package engine.repository;

import engine.model.QuizEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
interface QuizRepository extends CrudRepository<QuizEntity, Integer> {
}
