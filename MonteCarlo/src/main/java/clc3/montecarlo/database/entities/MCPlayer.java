package clc3.montecarlo.database.entities;

import at.hagenberg.master.montecarlo.entities.Player;

public class MCPlayer {
    private String name;
    private String teamName;
    private int elo;

    public MCPlayer() {}

    public MCPlayer(Player player) {
        this.name = player.getName();
        this.teamName = player.getTeamName();
        this.elo = player.getElo();
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getTeamName() { return teamName; }
    public void setTeamName(String teamName) { this.teamName = teamName; }
    public int getElo() { return elo; }
    public void setElo(int elo) { this.elo = elo; }
}