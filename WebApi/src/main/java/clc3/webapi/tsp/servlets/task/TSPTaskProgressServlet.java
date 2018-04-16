package clc3.webapi.tsp.servlets.task;

import clc3.common.servlets.requests.IdRequest;
import clc3.tsp.database.daos.TSPTaskConfigDao;
import clc3.tsp.database.daos.TSPTaskConfigRunDao;
import clc3.tsp.database.daos.TSPTaskConfigRunIterationDao;
import clc3.tsp.database.daos.TSPTaskDao;
import clc3.tsp.database.entities.TSPTask;
import clc3.tsp.database.entities.TSPTaskConfig;
import clc3.tsp.database.entities.TSPTaskConfigRun;
import clc3.tsp.database.entities.TSPTaskConfigRunIteration;
import clc3.webapi.tsp.model.TSPTaskProgressDetails;
import clc3.webapi.tsp.responses.TSPTaskProgressResponse;
import clc3.webapi.tsp.utils.IdRequestServlet;
import com.googlecode.objectify.Key;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@WebServlet(urlPatterns = "/tsp/task/progress")
public class TSPTaskProgressServlet extends IdRequestServlet {

    TSPTaskDao taskDao = serviceLocator.getTspTaskDao();
    TSPTaskConfigDao configDao = serviceLocator.getTspTaskConfigDao();
    TSPTaskConfigRunDao configRunDao = serviceLocator.getTspTaskConfigRunDao();
    TSPTaskConfigRunIterationDao configRunIterationDao = serviceLocator.getTspTaskConfigRunIterationDao();

    @Override
    protected void process(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        IdRequest req = (IdRequest)getParameters(request);
        TSPTaskProgressResponse respProgress = new TSPTaskProgressResponse();
        Map<Long, TSPTaskProgressDetails> taskConfigProgressMap = new HashMap<>();
        Map<Long, TSPTaskProgressDetails> taskConfigRunProgressMap = new HashMap<>();
        Map<Long, Integer> taskConfigRuns = new HashMap<>();

        Date dateStarted = null;
        Date dateCompleted = null;
        double bestFitness = Double.MAX_VALUE;
        List<Integer> bestTour = new ArrayList<>();

        int taskTotalIterations = 0;
        int taskCurrentIterations = 0;
        double bestTotalFitness = Double.MAX_VALUE;
        Key<TSPTask> taskKey = taskDao.getKey(req.getId());
        for (TSPTaskConfigRun run : configRunDao.getByTask(taskKey)) {
            TSPTaskConfig config = run.getConfig();
            int maxRunIteration = config.getIterations();
            int curRunIteration = 0;
            double curRunFitness = Double.MAX_VALUE;

            if (dateStarted == null) { dateStarted = run.getStarted(); }
            if (dateCompleted == null) { dateCompleted = run.getCompleted(); }

            if (dateStarted != null && run.getStarted() != null) {
                if (dateStarted.after(run.getStarted())) {
                    dateStarted = run.getStarted();
                }
            }
            if (dateCompleted != null && run.getCompleted() != null) {
                if (dateCompleted.before(run.getCompleted())) {
                    dateCompleted = run.getCompleted();
                }
            }

            taskConfigRuns.put(config.getId(), config.getRuns());

            if (run.getLastIterationRef() != null) {
                TSPTaskConfigRunIteration lastIteration = run.getLastIteration(); // no performance issues due to @Load
                if (lastIteration != null) {
                    curRunIteration += lastIteration.getIteration() + 1;
                    curRunFitness = lastIteration.getFitness();
                    if (curRunFitness < bestFitness) {
                        bestFitness = curRunFitness;
                        bestTour = lastIteration.getTour();
                    }
                }
            }

            double runProgress = 0;
            if (maxRunIteration > 0) {
                runProgress = curRunIteration / (double)maxRunIteration;
            }
            TSPTaskProgressDetails runProgressDetails = new TSPTaskProgressDetails(runProgress, curRunFitness);
            runProgressDetails.setStatus(run.getStatus());
            runProgressDetails.setStarted(run.getStarted());
            runProgressDetails.setCompleted(run.getCompleted());
            taskConfigRunProgressMap.put(run.getId(), runProgressDetails);

            TSPTaskProgressDetails configProgress = taskConfigProgressMap.getOrDefault(config.getId(), new TSPTaskProgressDetails(0, Double.MAX_VALUE));
            if (curRunFitness < configProgress.getBestFitness()) {
                configProgress.setBestFitness(curRunFitness);
            }

            if (curRunFitness < bestTotalFitness) {
                bestTotalFitness = curRunFitness;
            }
            configProgress.setProgress(configProgress.getProgress() + runProgress);
            taskConfigProgressMap.put(config.getId(), configProgress);

            taskTotalIterations += maxRunIteration;
            taskCurrentIterations += curRunIteration;
        }

        // adjust Config progress
        for (Map.Entry<Long, Integer> entrySet : taskConfigRuns.entrySet()) {
            TSPTaskProgressDetails configProgress = taskConfigProgressMap.getOrDefault(entrySet.getKey(), new TSPTaskProgressDetails(0, Double.MAX_VALUE));
            // summed progress / no of runs
            configProgress.setProgress(configProgress.getProgress() / entrySet.getValue());
            taskConfigProgressMap.put(entrySet.getKey(), configProgress);
        }

        double taskProgress = 0;
        if (taskTotalIterations > 0) {
            taskProgress = taskCurrentIterations / (double)taskTotalIterations;
        }

        TSPTaskProgressDetails taskDetails = new TSPTaskProgressDetails();
        taskDetails.setProgress(taskProgress);
        taskDetails.setBestFitness(bestTotalFitness);
        taskDetails.setStarted(dateStarted);
        // date completed is only valid if the taskProgress == 1
        taskDetails.setCompleted(dateCompleted);
        respProgress.setTaskDetails(taskDetails);

        // respProgress.setTaskProgress(taskProgress);
        respProgress.setTaskConfigProgress(taskConfigProgressMap);
        respProgress.setTaskConfigRunProgress(taskConfigRunProgressMap);
        respProgress.setBestTour(bestTour);
        jsonResponse(response, respProgress);
    }
}
