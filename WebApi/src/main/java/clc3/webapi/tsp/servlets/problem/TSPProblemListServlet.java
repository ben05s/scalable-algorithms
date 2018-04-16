package clc3.webapi.tsp.servlets.problem;

import clc3.tsp.database.daos.TSPProblemDao;
import clc3.tsp.database.entities.TSPProblem;
import clc3.tsp.servlets.BaseServlet;
import clc3.webapi.tsp.model.TSPCity;
import clc3.webapi.tsp.responses.TSPCityResponse;
import clc3.webapi.tsp.responses.TSPProblemResponse;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@WebServlet(urlPatterns = "/tsp/problem/list")
public class TSPProblemListServlet extends BaseServlet {
    TSPProblemDao problemDao = this.serviceLocator.getTspProblemDao();

    @Override // returns all problems
    protected void process(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Collection<TSPProblemResponse> respProblems = new ArrayList<>();
        for (TSPProblem problem : problemDao.getAll()) {
            TSPProblemResponse respProblem = new TSPProblemResponse();
            respProblem.setId(problem.getId());
            respProblem.setName(problem.getName());
            List<TSPCity> respCities = new ArrayList<>();
            for (clc3.tsp.database.entities.TSPCity dbCity : problem.getCities()) {
                respCities.add(new TSPCity(dbCity.getX(), dbCity.getY()));
            }
            respProblem.setCities(respCities);
            respProblems.add(respProblem);
        }
        jsonResponse(response, respProblems);
    }
}