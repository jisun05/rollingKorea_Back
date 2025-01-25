package history.traveler.rollingkorea.rank.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "RANK")
@Setter
@Getter
public class Rank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ranking_id")
    private Long rankingId;

    @Column(name = "place_id")
    private Long placeId;

    @Column
    private String rank;

    @Column(name = "count_like")
    private String countLike;


    @Column(name = "created_at")
    private LocalDateTime createdAt;



}
