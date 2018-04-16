package clc3.webapi.tsp.servlets.problem;

import clc3.common.servlet.responses.IdResponse;
import clc3.tsp.database.daos.TSPProblemDao;
import clc3.tsp.database.entities.TSPCity;
import clc3.tsp.database.entities.TSPProblem;
import clc3.tsp.servlets.BaseServlet;
import clc3.webapi.tsp.requests.TSPProblemAddRequest;
import com.googlecode.objectify.Key;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@WebServlet(urlPatterns = "/tsp/problem/add")
public class TSPProblemAddServlet extends BaseServlet {
    TSPProblemDao problemDao = this.serviceLocator.getTspProblemDao();

    private static final Logger log = Logger.getLogger(TSPProblemAddServlet.class.getName());

    @Override
    protected Object createEmptyParams() {
        return new TSPProblemAddRequest();
    }

    @Override // adds a new problem to the database
    protected void process(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        TSPProblemAddRequest req = getPostParameters(request, TSPProblemAddRequest.class);

        TSPProblem problem = new TSPProblem();
        problem.setName(req.getName());
        List<TSPCity> cities = new ArrayList<>();
        for (clc3.webapi.tsp.model.TSPCity city : req.getCities()) {
            cities.add(new TSPCity(city.getX(), city.getY()));
        }
        problem.setCities(cities);
        Key<TSPProblem> problemKey = problemDao.save(problem);

        IdResponse respId = new IdResponse();
        respId.setId(problemKey.getId());
        jsonResponse(response, respId);
    }
}