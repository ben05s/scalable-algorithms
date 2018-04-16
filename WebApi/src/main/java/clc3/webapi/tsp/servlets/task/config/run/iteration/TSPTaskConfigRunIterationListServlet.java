package clc3.webapi.tsp.servlets.task.config.run.iteration;

import clc3.common.servlets.requests.IdRequest;
import clc3.tsp.database.daos.TSPTaskConfigRunDao;
import clc3.tsp.database.daos.TSPTaskConfigRunIterationDao;
import clc3.tsp.database.entities.TSPTaskConfigRun;
import clc3.tsp.database.entities.TSPTaskConfigRunIteration;
import clc3.webapi.tsp.responses.TSPTaskConfigRunIterationResponse;
import clc3.webapi.tsp.utils.IdRequestServlet;
import com.googlecode.objectify.Key;

import javax.jws.WebService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

@WebServlet(urlPatterns = "/tsp/task/config/run/iterations")
public class TSPTaskConfigRunIterationListServlet extends IdRequestServlet {
    TSPTaskConfigRunDao taskConfigRunDao = serviceLocator.getTspTaskConfigRunDao();
    TSPTaskConfigRunIterationDao taskConfigRunIterationDao = serviceLocator.getTspTaskConfigRunIterationDao();

    @Override
    protected void process(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        IdRequest req = (IdRequest)getParameters(request);
        Key<TSPTaskConfigRun> taskConfigRunKey = taskConfigRunDao.getKey(req.getId());

        Collection<TSPTaskConfigRunIterationResponse> respTaskConfigRunIterations = new ArrayList<>();
        for (TSPTaskConfigRunIteration taskConfigRunIteration : taskConfigRunIterationDao.getByTaskConfigRunOrderByIteration(taskConfigRunKey)) {
            TSPTaskConfigRunIterationResponse respIteration = new TSPTaskConfigRunIterationResponse();
            respIteration.setIteration(taskConfigRunIteration.getIteration());
            respIteration.setFitness(taskConfigRunIteration.getFitness());
            respIteration.setTour(taskConfigRunIteration.getTour());
            respTaskConfigRunIterations.add(respIteration);
        }
        jsonResponse(response, respTaskConfigRunIterations);
    }
}