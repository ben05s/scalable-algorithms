package clc3.webapi.tsp.servlets.task.config.run;

import clc3.common.servlets.requests.IdRequest;
import clc3.tsp.database.daos.TSPTaskConfigDao;
import clc3.tsp.database.daos.TSPTaskConfigRunDao;
import clc3.tsp.database.daos.TSPTaskDao;
import clc3.tsp.database.entities.TSPTask;
import clc3.tsp.database.entities.TSPTaskConfig;
import clc3.tsp.database.entities.TSPTaskConfigRun;
import clc3.webapi.tsp.model.TSPTaskConfigRunIteration;
import clc3.webapi.tsp.responses.TSPTaskConfigRunResponse;
import clc3.webapi.tsp.utils.IdRequestServlet;
import com.googlecode.objectify.Key;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

@WebServlet(urlPatterns = "/tsp/task/config/runs/task")
public class TSPTaskConfigRunsListServlet extends IdRequestServlet {
    TSPTaskDao taskDao = serviceLocator.getTspTaskDao();
    TSPTaskConfigRunDao taskConfigRunDao = serviceLocator.getTspTaskConfigRunDao();

    @Override
    protected void process(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        IdRequest req = (IdRequest)getParameters(request);
        Key<TSPTask> taskKey = taskDao.getKey(req.getId());

        Collection<TSPTaskConfigRunResponse> respTaskConfigRuns = new ArrayList<>();
        for (TSPTaskConfigRun taskConfigRun : taskConfigRunDao.getByTask(taskKey)) {
            TSPTaskConfigRunResponse respTaskConfigRun = new TSPTaskConfigRunResponse();
            respTaskConfigRun.setId(taskConfigRun.getId());
            respTaskConfigRun.setConfigId(taskConfigRun.getConfig().getId());
            respTaskConfigRun.setMaxIteration(taskConfigRun.getConfig().getIterations());

            int status = taskConfigRun.getStatus();
            TSPTaskConfigRunIteration respIteration = new TSPTaskConfigRunIteration();
            clc3.tsp.database.entities.TSPTaskConfigRunIteration lastIteration = taskConfigRun.getLastIteration();
            if (lastIteration != null) {
                respIteration.setFitness(lastIteration.getFitness());
                respIteration.setIteration(lastIteration.getIteration());
                respIteration.setTour(lastIteration.getTour());
            }  else {
                respIteration.setFitness(Double.MAX_VALUE);
                respIteration.setIteration(0);
                respIteration.setTour(new ArrayList<>());
            }

            respTaskConfigRun.setLatestIteration(respIteration);
            respTaskConfigRun.setStatus(taskConfigRun.getStatus());
            respTaskConfigRun.setStarted(taskConfigRun.getStarted());
            respTaskConfigRun.setCompleted(taskConfigRun.getCompleted());
            respTaskConfigRuns.add(respTaskConfigRun);
        }
        jsonResponse(response, respTaskConfigRuns);
    }
}