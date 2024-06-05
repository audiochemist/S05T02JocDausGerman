package cat.itacademy.barcelonactiva.medina.jocdaus.s05.t01.n02.S05T02JocDausGerman.repositories;


import cat.itacademy.barcelonactiva.medina.jocdaus.s05.t01.n02.S05T02JocDausGerman.model.domain.GameEntity;
import cat.itacademy.barcelonactiva.medina.jocdaus.s05.t01.n02.S05T02JocDausGerman.model.domain.PlayerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface GameRepository extends JpaRepository<GameEntity, Long> {
    List<GameEntity> findByPlayer_PlayerID(long playerId);
    void deleteAllByPlayer(PlayerEntity player);
}


