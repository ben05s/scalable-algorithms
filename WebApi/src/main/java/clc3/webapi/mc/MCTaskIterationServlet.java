package clc3.webapi.mc;
import clc3.common.servlets.requests.*;
import clc3.montecarlo.database.daos.*;
import clc3.montecarlo.database.entities.*;
import clc3.webapi.mc.responses.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@WebServlet(urlPatterns = "/mc/task/iterations")
public class MCTaskIterationServlet extends BaseServlet {

    private MCTaskIterationDao taskIterationDao = serviceLocator.getMcTaskIterationDao();

    @Override
    protected Object createEmptyParams() {
        return new IdRequest();
    }

    @Override
    protected void process(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        IdRequest req = (IdRequest)getParameters(request);
        List<MCTaskIterationResponse> resp = new ArrayList<>();
        Collection<MCTaskIteration> iterations = taskIterationDao.getByMcTaskKey(MCTask.key(req.getId()));
        for (MCTaskIteration iteration : iterations) {
            MCTaskIterationResponse iterResp = new MCTaskIterationResponse();
            iterResp.setIteration(iteration.getIteration());
            iterResp.setSeasonResult(iteration.getSeasonResult());
            resp.add(iterResp);
        }
        jsonResponse(response, resp);
    }
}