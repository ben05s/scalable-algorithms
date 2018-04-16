package clc3.webapi.tsp.servlets;

import clc3.tsp.implementation.TSPOperatorManager;
import clc3.tsp.servlets.BaseServlet;
import clc3.webapi.tsp.responses.TSPOperatorsResponse;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/tsp/operators")
public class TSPOperatorsServlet extends BaseServlet {

    private TSPOperatorManager tspOperatorManager = serviceLocator.getTspOperatorManager();

    @Override
    protected void process(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws ServletException, IOException {
        TSPOperatorsResponse resp = new TSPOperatorsResponse();
        resp.setCrossover(tspOperatorManager.getCrossoverTypes());
        resp.setMutation(tspOperatorManager.getMutationTypes());
        resp.setSelection(tspOperatorManager.getSelectionTypes());
        jsonResponse(httpServletResponse, resp);
    }
}