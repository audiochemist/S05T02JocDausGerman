package cat.itacademy.barcelonactiva.medina.jocdaus.s05.t01.n02.S05T02JocDausGerman.repositories;

import cat.itacademy.barcelonactiva.medina.jocdaus.s05.t01.n02.S05T02JocDausGerman.model.domain.PlayerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface PlayerRepository extends JpaRepository<PlayerEntity, Long> {
    Optional<PlayerEntity> findByPlayerName(String playerName);

    PlayerEntity findTopByOrderByWinRateAsc();

    PlayerEntity findTopByOrderByWinRateDesc();

    List<PlayerEntity> findAllByOrderByWinRateDesc();
}
