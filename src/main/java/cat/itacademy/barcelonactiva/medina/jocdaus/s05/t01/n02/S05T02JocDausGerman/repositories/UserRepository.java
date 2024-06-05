package cat.itacademy.barcelonactiva.medina.jocdaus.s05.t01.n02.S05T02JocDausGerman.repositories;

import cat.itacademy.barcelonactiva.medina.jocdaus.s05.t01.n02.S05T02JocDausGerman.model.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<UserEntity, String> {

    Optional<UserEntity> findUserByEmail(String email);
    Optional<UserEntity> findUserByUserName(String userName);
}
