package clc3.montecarlo.database.entities;

import at.hagenberg.master.montecarlo.entities.Player;
import at.hagenberg.master.montecarlo.simulation.ChessPredictionModel;
import at.hagenberg.master.montecarlo.simulation.HeadToHeadMatch;

public class MCHeadToHeadMatch {

    private ChessPredictionModel predictionModel;

    private Player opponentA;
    private Player opponentB;

    private MCMatchResult matchPrediction;
    private MCMatchResult matchResult;

    public MCHeadToHeadMatch() {
    }

    public MCHeadToHeadMatch(HeadToHeadMatch match) {
        this.predictionModel = (ChessPredictionModel) match.getPredictionModel();
        this.opponentA = match.getOpponentA();
        this.opponentB = match.getOpponentB();
        this.matchResult = new MCMatchResult(match.getMatchResult());
    }

    public MCMatchResult getMatchPrediction() {
        return matchPrediction;
    }

    public MCMatchResult getMatchResult() {
        return matchResult;
    }

    public Player getOpponentA() {
        return opponentA;
    }

    public Player getOpponentB() {
        return opponentB;
    }

    public ChessPredictionModel getPredictionModel() {
        return predictionModel;
    }

    public void setPredictionModel(ChessPredictionModel predictionModel) {
        this.predictionModel = predictionModel;
    }

    public void setOpponentA(Player opponentA) {
        this.opponentA = opponentA;
    }

    public void setOpponentB(Player opponentB) {
        this.opponentB = opponentB;
    }

    public void setMatchPrediction(MCMatchResult matchPrediction) {
        this.matchPrediction = matchPrediction;
    }

    public void setMatchResult(MCMatchResult matchResult) {
        this.matchResult = matchResult;
    }
}
