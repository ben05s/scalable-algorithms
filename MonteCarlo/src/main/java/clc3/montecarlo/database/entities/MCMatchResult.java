package clc3.montecarlo.database.entities;

import at.hagenberg.master.montecarlo.entities.MatchResult;
import at.hagenberg.master.montecarlo.entities.Player;

import java.util.ArrayList;
import java.util.List;

import com.googlecode.objectify.annotation.Ignore;

public class MCMatchResult {

    private Player opponentA;
    private Player opponentB;
    private double scoreA;
    private double scoreB;

    private Player winner;

    @Ignore private List<MCHeadToHeadMatch> headToHeadMatches = new ArrayList<>();

    public MCMatchResult() {
    }

    public MCMatchResult(MatchResult result) {
        this.opponentA = (Player) result.getOpponentA();
        this.opponentB = (Player) result.getOpponentB();
        this.scoreA = result.getScoreA();
        this.scoreB = result.getScoreB();
        this.winner = (Player) result.getWinner();
        List<MCHeadToHeadMatch> list = new ArrayList<>();
        for (int i = 0; i < result.getHeadToHeadMatches().size(); i++) {
            list.add(new MCHeadToHeadMatch(result.getHeadToHeadMatches().get(i)));
        }
        this.headToHeadMatches = list;
    }

    public double getAbsoluteScore() {
        return Math.abs(scoreA - scoreB);
    }
 
    public Player getOpponentA() {
        return opponentA;
    }

    public Player getOpponentB() {
        return opponentB;
    }

    public double getScoreA() {
        return scoreA;
    }

    public double getScoreB() {
        return scoreB;
    }

    public Player getWinner() {
        return winner;
    }

    public List<MCHeadToHeadMatch> getHeadToHeadMatches() { 
        return headToHeadMatches; 
    }
}
