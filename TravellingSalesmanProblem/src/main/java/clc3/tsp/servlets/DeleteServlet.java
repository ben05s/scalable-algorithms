package clc3.tsp.servlets;

import clc3.tsp.database.daos.TSPTaskConfigDao;
import clc3.tsp.database.daos.TSPTaskConfigRunDao;
import clc3.tsp.database.daos.TSPTaskConfigRunIterationDao;
import clc3.tsp.database.daos.TSPTaskDao;
import clc3.tsp.database.entities.*;
import clc3.tsp.database.enums.TaskStatus;
import clc3.tsp.implementation.TSP;
import clc3.tsp.implementation.TSPOperatorManager;
import clc3.tsp.interfaces.GeneticAlgorithm;
import clc3.tsp.interfaces.Individual;
import clc3.tsp.servlets.requests.TSPProcessRequest;
import com.google.common.primitives.Ints;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/delete")
public class DeleteServlet extends BaseServlet {
    private static final Logger log = Logger.getLogger(ProcessServlet.class.getName());

    private TSPOperatorManager tspOperatorManager = serviceLocator.getTspOperatorManager();
    private TSPTaskDao taskDao = serviceLocator.getTspTaskDao();
    private TSPTaskConfigDao taskConfigDao = serviceLocator.getTspTaskConfigDao();
    private TSPTaskConfigRunDao taskConfigRunDao = serviceLocator.getTspTaskConfigRunDao();
    private TSPTaskConfigRunIterationDao taskConfigRunIterationDao = serviceLocator.getTspTaskConfigRunIterationDao();

    @Override
    protected Object createEmptyParams() {
        return new TSPProcessRequest();
    }

    @Override
    protected void process(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        TSPProcessRequest req = (TSPProcessRequest)getParameters(request);

        long configId = req.getId();

        TSPTaskConfig taskConfig = taskConfigDao.getById(configId);
        for (TSPTaskConfigRun configRun : taskConfigRunDao.getByConfig(taskConfigDao.getKey(configId))) {
            Key<TSPTaskConfigRun> runKey = taskConfigRunDao.getKey(configRun.getId());
            for (TSPTaskConfigRunIteration runIteration : taskConfigRunIterationDao.getByTaskConfigRun(runKey)) {
                taskConfigRunIterationDao.delete(runIteration);
            }
            taskConfigRunDao.delete(configRun);
        }
        taskConfigDao.delete(taskConfig);
        response.setStatus(HttpServletResponse.SC_OK);
    }
}