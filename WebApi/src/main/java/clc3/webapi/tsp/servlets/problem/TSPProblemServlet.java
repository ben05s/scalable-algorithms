package clc3.webapi.tsp.servlets.problem;

import clc3.common.servlets.requests.IdRequest;
import clc3.tsp.database.daos.TSPProblemDao;
import clc3.tsp.database.entities.TSPProblem;
import clc3.webapi.tsp.model.TSPCity;
import clc3.webapi.tsp.responses.TSPProblemResponse;
import clc3.webapi.tsp.utils.IdRequestServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = "/tsp/problem")
public class TSPProblemServlet extends IdRequestServlet {
    TSPProblemDao problemDao = this.serviceLocator.getTspProblemDao();

    @Override // returns a problem
    protected void process(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        IdRequest req = (IdRequest)getParameters(request);
        TSPProblem problem = problemDao.getById(req.getId());

        TSPProblemResponse respProblem = new TSPProblemResponse();
        respProblem.setId(problem.getId());
        respProblem.setName(problem.getName());
        List<TSPCity> respCities = new ArrayList<>();
        for (clc3.tsp.database.entities.TSPCity dbCity : problem.getCities()) {
            respCities.add(new TSPCity(dbCity.getX(), dbCity.getY()));
        }
        respProblem.setCities(respCities);
        jsonResponse(response, respProblem);
    }
}