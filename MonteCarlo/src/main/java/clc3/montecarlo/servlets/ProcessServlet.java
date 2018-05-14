package clc3.montecarlo.servlets;

import clc3.montecarlo.database.daos.*;
import clc3.montecarlo.database.entities.MCHeadToHeadMatch;
import clc3.montecarlo.database.entities.MCPlayer;
import clc3.montecarlo.database.entities.MCSeasonResult;
import clc3.montecarlo.database.entities.MCSettings;
import clc3.montecarlo.database.entities.MCTask;
import clc3.montecarlo.database.entities.MCTaskIteration;
import clc3.montecarlo.database.entities.MCTaskResult;
import clc3.montecarlo.database.entities.MCTeam;
import clc3.montecarlo.database.enums.*;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.googlecode.objectify.Key;

import org.apache.commons.math3.random.RandomGenerator;
import org.apache.commons.math3.random.Well19937c;

import at.hagenberg.master.montecarlo.parser.PgnAnalysis;
import at.hagenberg.master.montecarlo.entities.*;
import at.hagenberg.master.montecarlo.entities.enums.GameResult;
import at.hagenberg.master.montecarlo.entities.enums.LineupStrategy;
import at.hagenberg.master.montecarlo.simulation.ChessLeagueSimulation;
import at.hagenberg.master.montecarlo.prediction.ChessPredictionModel;
import at.hagenberg.master.montecarlo.simulation.HeadToHeadMatch;
import at.hagenberg.master.montecarlo.lineup.LineupSelector;
import at.hagenberg.master.montecarlo.simulation.settings.LeagueSettings;

import java.util.*;

@WebServlet(urlPatterns = "/process")
public class ProcessServlet extends BaseServlet {

    private MCTaskDao taskDao = serviceLocator.getMcTaskDao();
    private MCTaskIterationDao taskIterationDao = serviceLocator.getMcTaskIterationDao();
    private MCTaskResultDao taskResultDao = serviceLocator.getMcTaskResultDao();

    @Override
    protected void process(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        long start = System.currentTimeMillis();
        Long taskId = Long.parseLong(request.getParameter("taskId"));
        Long iterationStart = Long.parseLong(request.getParameter("iterationStart"));
        Long iterationEnd = Long.parseLong(request.getParameter("iterationEnd"));
        
        MCTask task = taskDao.getById(taskId);
        
        if(task.getStarted() == null) saveTaskToStarted(task);

        List<MCTaskIteration> iterations = new ArrayList<>();

        try {
            MCSettings mcSettings = task.getMCSettings();

            RandomGenerator randomGenerator = new Well19937c();
            final int gamesPerMatch = mcSettings.getGamesPerMatch();
            final int roundsPerSeason = mcSettings.getRoundsPerSeason();
            final int roundsToSimulate = mcSettings.getRoundsToSimulate();
            LineupSelector lineupSelector = new LineupSelector(LineupStrategy.valueOf(mcSettings.getLineupStrategy()), gamesPerMatch);
            
            ChessPredictionModel predictionModel = mcSettings.getPredictionModel();
            
            List<Team> teamList = new ArrayList<>();
            for(int i=0; i < task.getTeams().size(); i++) {
                MCTeam mcTeam = task.getTeams().get(i);
                Team team = new Team(mcTeam.getName());
                team.setPlayerList(mcTeam.getPlayerList());
                team.setAverageElo(mcTeam.getAverageElo());
                team.setStdDeviationElo(mcTeam.getStdDeviationElo());
                team.setLineup(PgnAnalysis.transposeLineupProbabilities(team.getPlayerList(), gamesPerMatch));
                teamList.add(team);
            }

            Map<Integer, List<HeadToHeadMatch>> roundGameResults = new HashMap<>();
            for(Map.Entry<String, List<MCHeadToHeadMatch>> entry : task.getRoundGameResults().entrySet()) {
                List<HeadToHeadMatch> list = new ArrayList<>();
                for (int i = 0; i < entry.getValue().size(); i++) {
                    MCHeadToHeadMatch mcHeadToHeadMatch = entry.getValue().get(i);
                    GameResult gameResult = GameResult.valueOf(new Double(mcHeadToHeadMatch.getMatchResult().getAbsoluteScore()));
                    Player a = new Player(mcHeadToHeadMatch.getOpponentA().getName());
                    a.setTeamName(mcHeadToHeadMatch.getOpponentA().getTeamName());
                    a.setElo(mcHeadToHeadMatch.getOpponentA().getElo());
                    Player b = new Player(mcHeadToHeadMatch.getOpponentB().getName());
                    b.setTeamName(mcHeadToHeadMatch.getOpponentB().getTeamName());
                    b.setElo(mcHeadToHeadMatch.getOpponentB().getElo());
                    MatchResult matchResult = new MatchResult(a, b, gameResult);
                    list.add(new HeadToHeadMatch(randomGenerator, predictionModel, a, b, matchResult));
                }
                roundGameResults.put(new Integer(entry.getKey()), list);
            }

            LeagueSettings<Team> settings = new LeagueSettings(predictionModel, teamList, roundsPerSeason, lineupSelector, roundsToSimulate, roundGameResults);
            
            ChessLeagueSimulation simulation = new ChessLeagueSimulation(randomGenerator, settings);
            for(Long i = iterationStart; i < iterationEnd; i++) {
                SeasonResult result = simulation.runSimulation();
                MCTaskIteration mcIteration = saveIteration(i.intValue(), result, task.getKey());
                iterations.add(mcIteration);
            }

            saveResult(iterations, task.getKey(), start, iterationStart.intValue(), iterationEnd.intValue());
            response.setStatus(200); // sets the queue task to done
        } catch(Exception e) {
            e.printStackTrace();
            task.setStatus(TaskStatus.ERROR);
            response.setStatus(404);
        }
    }

    private void saveTaskToStarted(MCTask task) {
        task.setStatus(TaskStatus.STARTED);
        task.setStarted(new Date());
        taskDao.save(task);
    }

    private MCTaskIteration saveIteration(int currentIteration, SeasonResult result, Key<MCTask> taskKey) {
        MCTaskIteration mcIteration = new MCTaskIteration();
        mcIteration.setIteration(currentIteration);
        mcIteration.setMcTask(taskKey);
        mcIteration.setSeasonResult(new MCSeasonResult(result));
        taskIterationDao.save(mcIteration);
        return mcIteration;
    }

    private void saveResult(List<MCTaskIteration> iterations, Key<MCTask> taskKey, long start,
        int iterationStart, int iterationEnd) {
        MCTaskResult taskResult = new MCTaskResult();

        Map<String, TeamSimulationResult> teamResultMap = new HashMap<>();
        int totalIterations = iterations.size();
        for (MCTaskIteration iteration : iterations) {
            Map<String, SeasonScore> teamMap = iteration.getSeasonResult().getTeamSeasonScoreMap();
            Iterator<Map.Entry<String, SeasonScore>> itTeam = teamMap.entrySet().iterator();
            
            // promoted team
            if(itTeam.hasNext()) {
                Map.Entry<String, SeasonScore> entry = itTeam.next();
                TeamSimulationResult teamResult = teamResultMap.get(entry.getKey());
                if(teamResult == null) {
                    teamResult = new TeamSimulationResult(entry.getKey(), totalIterations);
                }
                teamResult.addPoints(new Double(entry.getValue().getSeasonPoints()).intValue());
                teamResult.addScore(entry.getValue().getPointsScored());
                teamResult.addPromotion();
                teamResult.setIterationStart(iterationStart);
                teamResult.setIterationEnd(iterationEnd);
    
                teamResultMap.put(entry.getKey(), teamResult);
            }
    
            int count = 1;
            while(itTeam.hasNext()) {
                count++;
                Map.Entry<String, SeasonScore> entry = itTeam.next();
                TeamSimulationResult teamResult = teamResultMap.get(entry.getKey());
                if(teamResult == null) {
                    teamResult = new TeamSimulationResult(entry.getKey(), totalIterations);
                }
                teamResult.addPoints(new Double(entry.getValue().getSeasonPoints()).intValue());
                teamResult.addScore(entry.getValue().getPointsScored());
                teamResult.setIterationStart(iterationStart);
                teamResult.setIterationEnd(iterationEnd);
                
                // relegated team
                if(count == teamMap.size()) teamResult.addRelegation();
                teamResultMap.put(entry.getKey(), teamResult);
            }
        }
        List<TeamSimulationResult> teamResults = new ArrayList<>(teamResultMap.values());
    
        taskResult.setMcTask(taskKey);
        taskResult.setTeamResults(teamResults);
        taskResult.setCacluatedIterations(totalIterations);
        taskResult.setComputeTimes(Arrays.asList(System.currentTimeMillis() - start));
        taskResultDao.save(taskResult);
    }
}