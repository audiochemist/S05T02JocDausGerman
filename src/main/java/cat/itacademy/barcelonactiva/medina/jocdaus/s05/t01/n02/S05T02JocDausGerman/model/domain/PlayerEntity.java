package cat.itacademy.barcelonactiva.medina.jocdaus.s05.t01.n02.S05T02JocDausGerman.model.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table (name="Player")
public class PlayerEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long playerID;

    @Column(name="Player_Name")
    private String playerName;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private final Date registerDate = new Date();

    @Column(name="Win_rate")
    private double winRate;

    @OneToMany(mappedBy = "player")
    private List<GameEntity> gamesList;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;


    public void addingGame(GameEntity game) {
        if (gamesList == null) {
            gamesList = new ArrayList<>();
        }
        gamesList.add(game);
    }

    public double calculateSuccessRate() {
        if (gamesList != null && !gamesList.isEmpty()) {
            long totalGames = gamesList.size();
            long wonGames = gamesList.stream().filter(GameEntity::hasWon).count();
            winRate =  wonGames / totalGames * 100;
            BigDecimal bd = new BigDecimal(winRate).setScale(2, RoundingMode.HALF_UP);
            return bd.doubleValue();
        }else{
            return 0;
        }
    }
}