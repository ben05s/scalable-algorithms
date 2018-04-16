package clc3.montecarlo.servlets;

import clc3.montecarlo.database.daos.*;
import clc3.montecarlo.database.entities.MCSeasonResult;
import clc3.montecarlo.database.entities.MCSettings;
import clc3.montecarlo.database.entities.MCTask;
import clc3.montecarlo.database.entities.MCTaskIteration;
import clc3.montecarlo.database.entities.MCTaskResult;
import clc3.montecarlo.database.enums.*;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.googlecode.objectify.Key;

import at.hagenberg.master.montecarlo.PgnAnalysis;
import at.hagenberg.master.montecarlo.entities.SeasonResult;
import at.hagenberg.master.montecarlo.entities.SeasonScore;
import at.hagenberg.master.montecarlo.entities.Team;
import at.hagenberg.master.montecarlo.entities.TeamResult;
import at.hagenberg.master.montecarlo.exceptions.ChessMonteCarloSimulationException;
import at.hagenberg.master.montecarlo.simulation.ChessGame;
import at.hagenberg.master.montecarlo.simulation.ChessPredictionModel;
import at.hagenberg.master.montecarlo.simulation.MonteCarloSimulation;
import at.hagenberg.master.montecarlo.simulation.settings.ChessLineupSettings;
import at.hagenberg.master.montecarlo.simulation.settings.MonteCarloSettings;
import at.hagenberg.master.montecarlo.simulation.settings.SeasonSettings;

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

            SeasonSettings seasonSettings = new SeasonSettings(mcSettings.getRoundsPerSeason(), mcSettings.getGamesPerMatch(), mcSettings.getRoundsToSimulate());
            ChessPredictionModel predictionModel = new ChessPredictionModel(mcSettings.isUseAdvWhite(), mcSettings.isUseStrengthTrend(), mcSettings.isUseStats(), mcSettings.isUseRegularization());
            
            List<Team> teams = null;
            Map<Integer, List<ChessGame>> roundGameResults = null;
            try{
                PgnAnalysis analysis = new PgnAnalysis(seasonSettings, mcSettings.getFileSeasonToSimulateContent(), mcSettings.getFileHistoricGamesContent());
                teams = analysis.getTeams();
                teams = predictionModel.regularizePlayerRatingsForTeams(teams);
                roundGameResults = analysis.getRoundGameResults();
                predictionModel.setStatistics(analysis);
            } catch(ChessMonteCarloSimulationException e) {
                throw new ServletException("Error processing PGNs");
            }
            
            ChessLineupSettings lineupSettings = new ChessLineupSettings(mcSettings.getLineupStrategy(), mcSettings.getOptimizeLineupTeamName());
            MonteCarloSettings monteCarloSettings = new MonteCarloSettings(seasonSettings, predictionModel, lineupSettings);
            
            MonteCarloSimulation simulation = new MonteCarloSimulation(monteCarloSettings, teams, roundGameResults);
            for(Long i = iterationStart; i < iterationEnd; i++) {
                SeasonResult result = simulation.runSimulation();
                MCTaskIteration mcIteration = saveIteration(i.intValue(), result, task.getKey());
                iterations.add(mcIteration);
            }

            saveResult(iterations, task.getKey(), start, iterationStart.intValue(), iterationEnd.intValue());
            response.setStatus(200); // sets the queue task to done
        } catch(Exception|ChessMonteCarloSimulationException e) {
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

        Map<String, TeamResult> teamResultMap = new HashMap<>();
        int totalIterations = iterations.size();
        for (MCTaskIteration iteration : iterations) {
            Map<String, SeasonScore> teamMap = iteration.getSeasonResult().getTeamSeasonScoreMap();
            Iterator<Map.Entry<String, SeasonScore>> itTeam = teamMap.entrySet().iterator();
            
            // promoted team
            if(itTeam.hasNext()) {
                Map.Entry<String, SeasonScore> entry = itTeam.next();
                TeamResult teamResult = teamResultMap.get(entry.getKey());
                if(teamResult == null) {
                    teamResult = new TeamResult(entry.getKey(), totalIterations);
                }
                teamResult.addPointsScored(entry.getValue().getPointsScored());
                teamResult.addPromotion();
                teamResult.setIterationStart(iterationStart);
                teamResult.setIterationEnd(iterationEnd);
    
                teamResultMap.put(entry.getKey(), teamResult);
            }
    
            int count = 1;
            while(itTeam.hasNext()) {
                count++;
                Map.Entry<String, SeasonScore> entry = itTeam.next();
                TeamResult teamResult = teamResultMap.get(entry.getKey());
                if(teamResult == null) {
                    teamResult = new TeamResult(entry.getKey(), totalIterations);
                }
                teamResult.addPointsScored(entry.getValue().getPointsScored());
                teamResult.setIterationStart(iterationStart);
                teamResult.setIterationEnd(iterationEnd);
                
                // relegated team
                if(count == teamMap.size()) teamResult.addRelegation();
                teamResultMap.put(entry.getKey(), teamResult);
            }
        }
        List<TeamResult> teamResults = new ArrayList<>(teamResultMap.values());
    
        taskResult.setMcTask(taskKey);
        taskResult.setTeamResults(teamResults);
        taskResult.setCacluatedIterations(totalIterations);
        taskResult.setComputeTimes(Arrays.asList(System.currentTimeMillis() - start));
        taskResultDao.save(taskResult);
    }
}