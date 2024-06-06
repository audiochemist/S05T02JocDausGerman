package cat.itacademy.barcelonactiva.medina.jocdaus.s05.t01.n02.S05T02JocDausGerman.model.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name="Game")
public class GameEntity {

        @Id
        @GeneratedValue(strategy=GenerationType.IDENTITY)
        private long gameID;

        @Column(name="Die_One")
        private int die1;

        @Column(name="Die_Two")
        private int die2;

        @Column(name="Result")
        private boolean won;

        @ManyToOne
        @JoinColumn(name="playerID")
        private PlayerEntity player;
        public boolean play() {
                die1 = (int) (Math.random() * 6 + 1);
                die2 = (int) (Math.random() * 6 + 1);
                won = die1 + die2 == 7;
                return won;
        }

        public boolean hasWon() {
                return won;
        }
}
