package clc3.webapi.tsp.servlets.problem;

import clc3.common.servlets.requests.IdRequest;
import clc3.tsp.database.daos.TSPProblemDao;
import clc3.tsp.database.entities.TSPCity;
import clc3.tsp.database.entities.TSPProblem;
import clc3.webapi.tsp.responses.TSPCityResponse;
import clc3.webapi.tsp.utils.IdRequestServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;


@WebServlet(urlPatterns = "/tsp/problem/cities")
public class TSPProblemCitiesServlet extends IdRequestServlet {
    TSPProblemDao problemDao = this.serviceLocator.getTspProblemDao();

    @Override // returns cities for a specific problem
    protected void process(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        IdRequest req = (IdRequest)getParameters(request);
        Collection<TSPCityResponse> respCities = new ArrayList<>();
        TSPProblem problem = problemDao.getById(req.getId());
        for (TSPCity dbCity : problem.getCities()) {
            respCities.add(new TSPCityResponse(dbCity.getX(), dbCity.getY()));
        }
        jsonResponse(response, respCities);
    }
}