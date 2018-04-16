package clc3.webapi.mc.responses;

import java.util.*;

import at.hagenberg.master.montecarlo.entities.Team;

public class ChessLeagueResponse {
    String leagueName;
    List<Team> teams;
    String pgnFileKey;
    
    public ChessLeagueResponse() {}

    public String getLeagueName() { return this.leagueName; }
    public void setLeagueName(String leagueName) { this.leagueName = leagueName; }
    public List<Team> getTeams() { return this.teams; }
    public void setTeams(List<Team> teams) { this.teams = teams; }
    public String getPgnFileKey() { return this.pgnFileKey; }
    public void setPgnFileKey(String pgnFileKey) { this.pgnFileKey = pgnFileKey; }
}
