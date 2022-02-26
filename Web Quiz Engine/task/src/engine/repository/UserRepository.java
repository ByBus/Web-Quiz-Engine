package engine.repository;

import engine.model.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public
interface UserRepository extends CrudRepository<UserEntity, String> { }
