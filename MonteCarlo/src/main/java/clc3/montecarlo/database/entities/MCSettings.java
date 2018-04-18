package clc3.montecarlo.database.entities;

import java.util.List;
import java.util.Map;

import com.googlecode.objectify.annotation.Ignore;

import at.hagenberg.master.montecarlo.simulation.ChessPredictionModel;

public class MCSettings {
    private int roundsPerSeason;
    private int gamesPerMatch;
    private int roundsToSimulate;
    private int ratingSystem;
    private boolean useAdvWhite;
    private boolean useStrengthTrend;
    private boolean useStats;
    private boolean useRegularization;
    private int lineupStrategy;
    private String optimizeLineupTeamName;

    @Ignore private String fileSeasonToSimulateContent;
    @Ignore private String fileHistoricGamesContent;

    private ChessPredictionModel predictionModel;
    private List<MCTeam> teams;
    private Map<String, List<MCHeadToHeadMatch>> roundGameResults;

    public ChessPredictionModel getPredictionModel() {
        return predictionModel;
    }

    public void setPredictionModel(ChessPredictionModel predictionModel) {
        this.predictionModel = predictionModel;
    }

    public List<MCTeam> getTeams() { 
        return teams;
    }

    public void setTeams(List<MCTeam> teams) { 
        this.teams = teams; 
    }

    public Map<String, List<MCHeadToHeadMatch>> getRoundGameResults() {
        return roundGameResults;
    }

    public void setRoundGameResults(Map<String, List<MCHeadToHeadMatch>> roundGameResults) {
        this.roundGameResults = roundGameResults;
    }

    public MCSettings() {
    }

    public int getRoundsPerSeason() {
        return roundsPerSeason;
    }

    public void setRoundsPerSeason(int roundsPerSeason) {
        this.roundsPerSeason = roundsPerSeason;
    }

    public int getGamesPerMatch() {
        return gamesPerMatch;
    }

    public void setGamesPerMatch(int gamesPerMatch) {
        this.gamesPerMatch = gamesPerMatch;
    }

    public int getRoundsToSimulate() {
        return roundsToSimulate;
    }

    public void setRoundsToSimulate(int roundsToSimulate) {
        this.roundsToSimulate = roundsToSimulate;
    }

    public int getRatingSystem() {
        return ratingSystem;
    }

    public void setRatingSystem(int ratingSystem) {
        this.ratingSystem = ratingSystem;
    }

    public boolean isUseAdvWhite() {
        return useAdvWhite;
    }

    public void setUseAdvWhite(boolean useAdvWhite) {
        this.useAdvWhite = useAdvWhite;
    }

    public boolean isUseStrengthTrend() {
        return useStrengthTrend;
    }

    public void setUseStrengthTrend(boolean useStrengthTrend) {
        this.useStrengthTrend = useStrengthTrend;
    }

    public boolean isUseStats() {
        return useStats;
    }

    public void setUseStats(boolean useStats) {
        this.useStats = useStats;
    }

    public boolean isUseRegularization() {
        return useRegularization;
    }

    public void setUseRegularization(boolean useRegularization) {
        this.useRegularization = useRegularization;
    }

    public int getLineupStrategy() {
        return lineupStrategy;
    }

    public void setLineupStrategy(int lineupStrategy) {
        this.lineupStrategy = lineupStrategy;
    }

    public String getOptimizeLineupTeamName() {
        return optimizeLineupTeamName;
    }

    public void setOptimizeLineupTeamName(String optimizeLineupTeamName) {
        this.optimizeLineupTeamName = optimizeLineupTeamName;
    }
    
    public String getFileHistoricGamesContent() { return this.fileHistoricGamesContent; }
    public void setFileHistoricGamesContent(String fileHistoricGamesContent) { this.fileHistoricGamesContent = fileHistoricGamesContent; }
    public String getFileSeasonToSimulateContent() { return this.fileSeasonToSimulateContent; }
    public void setFileSeasonToSimulateContent(String fileSeasonToSimulateContent) { this.fileSeasonToSimulateContent = fileSeasonToSimulateContent; }
}
