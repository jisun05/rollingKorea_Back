package history.traveler.rollingkorea.ranking.controller;
import java.time.LocalDateTime;

public record RankingResponse(
        Long rankingId,
        Long placeId,
        String position,
        int countLike,
        LocalDateTime createdAt
) {}
