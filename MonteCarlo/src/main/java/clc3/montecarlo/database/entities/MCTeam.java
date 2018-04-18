package clc3.montecarlo.database.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.googlecode.objectify.annotation.Ignore;

import at.hagenberg.master.montecarlo.PgnAnalysis;
import at.hagenberg.master.montecarlo.entities.Player;
import at.hagenberg.master.montecarlo.entities.Team;

public class MCTeam {
    private String name;
    List<Player> playerList;
    private double averageElo = 0.0;
    private double stdDeviationElo = 0.0;

    public MCTeam() {
    }

    public MCTeam(Team team, final int gamesPerMatch) {
        this.name = team.getName();
        this.playerList = team.getPlayerList();
        this.averageElo = team.getAverageElo();
        this.stdDeviationElo = team.getStdDeviationElo();
    }

    public String getName() { return name; }
    
    public void setName(String name) { this.name = name; }

    public List<Player> getPlayerList() {
        return playerList;
    }

    public void setPlayerList(List<Player> playerList) {
        this.playerList = playerList;
    }

    public double getAverageElo() {
        return averageElo;
    }

    public void setAverageElo(double averageElo) {
        this.averageElo = averageElo;
    }

    public double getStdDeviationElo() {
        return stdDeviationElo;
    }

    public void setStdDeviationElo(double stdDeviationElo) {
        this.stdDeviationElo = stdDeviationElo;
    }
}