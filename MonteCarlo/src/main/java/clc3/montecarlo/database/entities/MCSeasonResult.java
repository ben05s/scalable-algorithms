package clc3.montecarlo.database.entities;

import java.util.HashMap;
import java.util.Map;

import at.hagenberg.master.montecarlo.entities.SeasonResult;
import at.hagenberg.master.montecarlo.entities.SeasonScore;

public class MCSeasonResult {
    private Map<String, SeasonScore> teamSeasonScoreMap = new HashMap<>();

    public MCSeasonResult(SeasonResult seasonResult) {
        this.teamSeasonScoreMap = seasonResult.getTeamSeasonScoreMap();
    }

    public Map<String, SeasonScore> getTeamSeasonScoreMap() {
        return teamSeasonScoreMap;
    }

    public void setTeamSeasonScoreMap(Map<String, SeasonScore> teamSeasonScoreMap) {
        this.teamSeasonScoreMap = teamSeasonScoreMap;
    }
}
