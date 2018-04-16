package clc3.webapi.tsp.servlets.task.config;

import clc3.common.servlet.responses.IdResponse;
import clc3.tsp.database.daos.TSPProblemDao;
import clc3.tsp.database.daos.TSPTaskConfigDao;
import clc3.tsp.database.daos.TSPTaskConfigRunDao;
import clc3.tsp.database.daos.TSPTaskDao;
import clc3.tsp.database.entities.TSPProblem;
import clc3.tsp.database.entities.TSPTask;
import clc3.tsp.database.entities.TSPTaskConfig;
import clc3.tsp.database.entities.TSPTaskConfigRun;
import clc3.tsp.database.enums.TaskStatus;
import clc3.tsp.servlets.BaseServlet;
import clc3.webapi.tsp.requests.TSPTaskConfigAddRequest;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(urlPatterns = "/tsp/task/config/add")
public class TSPTaskConfigAddServlet extends BaseServlet {
    TSPTaskDao taskDao = serviceLocator.getTspTaskDao();
    TSPTaskConfigDao taskConfigDao = serviceLocator.getTspTaskConfigDao();
    TSPTaskConfigRunDao taskConfigRunDao = serviceLocator.getTspTaskConfigRunDao();

    @Override
    protected Object createEmptyParams() {
        return new TSPTaskConfigAddRequest();
    }

    @Override
    protected void process(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        TSPTaskConfigAddRequest req = getPostParameters(request, TSPTaskConfigAddRequest.class);

        Ref<TSPTask> taskRef = taskDao.getRef(req.getTaskId());

        TSPTaskConfig config = new TSPTaskConfig();
        config.setTask(taskRef);
        config.setSelection(req.getSelection());
        config.setCrossover(req.getCrossover());
        config.setMutation(req.getMutation());
        config.setMutationRate(req.getMutationRate());
        config.setElitism(req.isElitism());
        config.setPopulationSize(req.getPopulationSize());
        config.setIterations(req.getIterations());
        config.setRuns(req.getRuns());
        Key<TSPTaskConfig> configKey = taskConfigDao.save(config);

        Ref<TSPProblem> problemRef = taskRef.get().getProblemRef();
        for (int run = 0; run < req.getRuns(); ++run) {
            TSPTaskConfigRun configRun = new TSPTaskConfigRun();
            configRun.setMaxIteration(config.getIterations());
            configRun.setTask(taskRef);
            configRun.setProblem(problemRef);
            configRun.setConfig(taskConfigDao.getRef(configKey.getId()));
            configRun.setStatus(TaskStatus.IDLE);
            taskConfigRunDao.save(configRun);
        }

        IdResponse respId = new IdResponse();
        respId.setId(configKey.getId());
        jsonResponse(response, respId);
    }
}