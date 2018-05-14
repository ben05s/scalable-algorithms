package clc3.montecarlo.database.entities;

import com.googlecode.objectify.annotation.Ignore;

import at.hagenberg.master.montecarlo.prediction.ChessPredictionModel;
import at.hagenberg.master.montecarlo.simulation.HeadToHeadMatch;

public class MCHeadToHeadMatch {

    private ChessPredictionModel predictionModel;

    private MCPlayer opponentA;
    private MCPlayer opponentB;

    private MCMatchResult matchPrediction;
    private MCMatchResult matchResult;

    public MCHeadToHeadMatch() {
    }

    public MCHeadToHeadMatch(HeadToHeadMatch match) {
        this.predictionModel = (ChessPredictionModel) match.getPredictionModel();
        this.opponentA = new MCPlayer(match.getOpponentA());
        this.opponentB = new MCPlayer(match.getOpponentB());
        this.matchResult = new MCMatchResult(match.getMatchResult());
    }

    public MCMatchResult getMatchPrediction() {
        return matchPrediction;
    }

    public MCMatchResult getMatchResult() {
        return matchResult;
    }

    public MCPlayer getOpponentA() {
        return opponentA;
    }

    public MCPlayer getOpponentB() {
        return opponentB;
    }

    public ChessPredictionModel getPredictionModel() {
        return predictionModel;
    }

    public void setPredictionModel(ChessPredictionModel predictionModel) {
        this.predictionModel = predictionModel;
    }

    public void setOpponentA(MCPlayer opponentA) {
        this.opponentA = opponentA;
    }

    public void setOpponentB(MCPlayer opponentB) {
        this.opponentB = opponentB;
    }

    public void setMatchPrediction(MCMatchResult matchPrediction) {
        this.matchPrediction = matchPrediction;
    }

    public void setMatchResult(MCMatchResult matchResult) {
        this.matchResult = matchResult;
    }
}
