package engine.repository;

import engine.model.CorrectQuizAnswerEntity;
import engine.model.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CorrectQuizAnswerPagingRepository extends PagingAndSortingRepository<CorrectQuizAnswerEntity, Integer> {

    Page<CorrectQuizAnswerEntity> findAllByUser(UserEntity user, Pageable pageable);
}