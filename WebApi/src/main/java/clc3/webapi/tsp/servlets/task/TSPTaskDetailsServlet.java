package clc3.webapi.tsp.servlets.task;

import clc3.common.servlets.requests.IdRequest;
import clc3.tsp.database.daos.TSPTaskDao;
import clc3.tsp.database.entities.TSPTask;
import clc3.tsp.servlets.BaseServlet;
import clc3.webapi.tsp.model.TSPCity;
import clc3.webapi.tsp.model.TSPProblem;
import clc3.webapi.tsp.model.TSPTaskConfigRunIteration;
import clc3.webapi.tsp.responses.TSPTaskResponse;
import clc3.webapi.tsp.utils.IdRequestServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@WebServlet(urlPatterns = "/tsp/task")
public class TSPTaskDetailsServlet extends IdRequestServlet {

    private TSPTaskDao taskDao = serviceLocator.getTspTaskDao();

    @Override
    protected void process(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        IdRequest req = (IdRequest)getParameters(request);
        TSPTask task = taskDao.getById(req.getId());

        TSPTaskResponse respTask = new TSPTaskResponse();
        respTask.setId(task.getId());
        respTask.setName(task.getName());

        TSPProblem respProblem = new TSPProblem();
        clc3.tsp.database.entities.TSPProblem dbProblem = task.getProblem();
        respProblem.setId(dbProblem.getId());
        respProblem.setName(dbProblem.getName());

        List<TSPCity> respCities = new ArrayList<>();
        for (clc3.tsp.database.entities.TSPCity dbCity : dbProblem.getCities()) {
            respCities.add(new TSPCity(dbCity.getX(), dbCity.getY()));
        }
        respProblem.setCities(respCities);
        respTask.setProblem(respProblem);

        TSPTaskConfigRunIteration respRunIteration = new TSPTaskConfigRunIteration();
        clc3.tsp.database.entities.TSPTaskConfigRunIteration dbRunIteration = task.getBestIteration();
        if (dbRunIteration != null) {
            respRunIteration.setFitness(dbRunIteration.getFitness());
            respRunIteration.setIteration(dbRunIteration.getIteration());
            respRunIteration.setTour(dbRunIteration.getTour());
        }
        respTask.setBestIteration(respRunIteration);
        respTask.setCreated(task.getCreated());
        respTask.setStarted(task.getStarted());

        jsonResponse(response, respTask);
    }
}