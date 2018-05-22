package clc3.montecarlo.database.entities;

import java.util.HashMap;
import java.util.Map;

import at.hagenberg.master.montecarlo.entities.SeasonResult;
import at.hagenberg.master.montecarlo.entities.SeasonScore;

public class MCSeasonResult {
    private Map<String, SeasonScore> teamSeasonScoreMap = new HashMap<>();
    private double promotionError = 0.0;
    private double relegationError = 0.0;

    public MCSeasonResult(SeasonResult seasonResult) {
        this.teamSeasonScoreMap = seasonResult.getTeamSeasonScoreMap();
        this.promotionError = seasonResult.getPromotionError();
        this.relegationError = seasonResult.getRelegationError();
    }

    public Map<String, SeasonScore> getTeamSeasonScoreMap() {
        return teamSeasonScoreMap;
    }

    public void setTeamSeasonScoreMap(Map<String, SeasonScore> teamSeasonScoreMap) {
        this.teamSeasonScoreMap = teamSeasonScoreMap;
    }

    public double getPromotionError() {
        return promotionError;
    }

    public void setPromotionError(double promotionError) {
        this.promotionError = promotionError;
    }

    public double getRelegationError() {
        return relegationError;
    }

    public void setRelegationError(double relegationError) {
        this.relegationError = relegationError;
    }
}
