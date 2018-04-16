package clc3.webapi.tsp.servlets.task.config;

import clc3.common.servlets.requests.IdRequest;
import clc3.tsp.database.daos.TSPTaskConfigDao;
import clc3.tsp.database.daos.TSPTaskDao;
import clc3.tsp.database.entities.TSPTask;
import clc3.tsp.database.entities.TSPTaskConfig;
import clc3.tsp.servlets.BaseServlet;
import clc3.webapi.tsp.responses.TSPTaskConfigResponse;
import clc3.webapi.tsp.utils.IdRequestServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = "/tsp/task/config/list")
public class TSPTaskConfigListServlet extends IdRequestServlet {
    TSPTaskDao taskDao = serviceLocator.getTspTaskDao();
    TSPTaskConfigDao taskConfigDao = serviceLocator.getTspTaskConfigDao();

    @Override
    protected void process(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        IdRequest req = (IdRequest)getParameters(request);
        List<TSPTaskConfigResponse> respConfigs = new ArrayList<>();
        for (TSPTaskConfig config : taskConfigDao.getByTaskKey(taskDao.getKey(req.getId()))) {
            TSPTaskConfigResponse respConfig = new TSPTaskConfigResponse();
            respConfig.setTaskConfigId(config.getId());
            respConfig.setTaskId(config.getTask().getId());
            respConfig.setSelection(config.getSelection());
            respConfig.setCrossover(config.getCrossover());
            respConfig.setMutation(config.getMutation());
            respConfig.setMutationRate(config.getMutationRate());
            respConfig.setElitism(config.isElitism());
            respConfig.setPopulationSize(config.getPopulationSize());
            respConfig.setIterations(config.getIterations());
            respConfig.setRuns(config.getRuns());
            respConfigs.add(respConfig);
        }
        jsonResponse(response, respConfigs);
    }
}