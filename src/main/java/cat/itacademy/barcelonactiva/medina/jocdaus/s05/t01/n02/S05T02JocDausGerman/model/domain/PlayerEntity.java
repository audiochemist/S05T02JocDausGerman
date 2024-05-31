package cat.itacademy.barcelonactiva.medina.jocdaus.s05.t01.n02.S05T02JocDausGerman.model.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

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

    @Column(name="Register_Date")
    private LocalDateTime registerDate;

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

    public float calculateSuccessRate() {
        if (gamesList != null && !gamesList.isEmpty()) {
            long totalGames = gamesList.size();
            long wonGames = gamesList.stream().filter(GameEntity::hasWon).count();
            return (float) wonGames / totalGames * 100;
        }
        return 0.0f;
    }
}