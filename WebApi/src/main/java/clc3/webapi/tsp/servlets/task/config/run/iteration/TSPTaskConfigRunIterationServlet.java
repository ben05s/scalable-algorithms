package clc3.webapi.tsp.servlets.task.config.run.iteration;

import clc3.tsp.database.daos.TSPTaskConfigRunDao;
import clc3.tsp.database.daos.TSPTaskConfigRunIterationDao;
import clc3.tsp.database.entities.TSPTaskConfigRun;
import clc3.tsp.database.entities.TSPTaskConfigRunIteration;
import clc3.tsp.servlets.BaseServlet;
import clc3.webapi.tsp.requests.TSPTaskConfigRunIterationRequest;
import clc3.webapi.tsp.responses.TSPTaskConfigRunIterationResponse;
import com.googlecode.objectify.Key;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/tsp/task/config/run/iteration")
public class TSPTaskConfigRunIterationServlet extends BaseServlet {
    TSPTaskConfigRunDao taskConfigRunDao = serviceLocator.getTspTaskConfigRunDao();
    TSPTaskConfigRunIterationDao taskConfigRunIterationDao = serviceLocator.getTspTaskConfigRunIterationDao();

    @Override
    protected Object createEmptyParams() {
        return new TSPTaskConfigRunIterationRequest();
    }

    @Override
    protected void process(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        TSPTaskConfigRunIterationRequest req = (TSPTaskConfigRunIterationRequest)getParameters(request);
        Key<TSPTaskConfigRun> key = taskConfigRunDao.getKey(req.getId());

        TSPTaskConfigRunIteration iteration = taskConfigRunIterationDao.getByTaskConfigRunAndIteration(key, req.getIteration());
        TSPTaskConfigRunIterationResponse respIteration = new TSPTaskConfigRunIterationResponse();
        if (iteration == null) {
            iteration = taskConfigRunIterationDao.getLatestIteration(key);
        }
        respIteration.setIteration(iteration.getIteration());
        respIteration.setFitness(iteration.getFitness());
        respIteration.setTour(iteration.getTour());

        jsonResponse(response, respIteration);
    }
}