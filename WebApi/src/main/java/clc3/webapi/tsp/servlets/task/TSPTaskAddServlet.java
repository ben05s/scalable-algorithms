package clc3.webapi.tsp.servlets.task;

import clc3.common.servlet.responses.IdResponse;
import clc3.tsp.database.daos.TSPProblemDao;
import clc3.tsp.database.daos.TSPTaskDao;
import clc3.tsp.database.entities.TSPTask;
import clc3.tsp.database.enums.TaskStatus;
import clc3.tsp.servlets.BaseServlet;
import clc3.webapi.tsp.requests.TSPTaskAddRequest;
import com.googlecode.objectify.Key;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@WebServlet(urlPatterns = "/tsp/task/add")
public class TSPTaskAddServlet extends BaseServlet {
    TSPTaskDao taskDao = serviceLocator.getTspTaskDao();
    TSPProblemDao problemDao = serviceLocator.getTspProblemDao();

    @Override
    protected Object createEmptyParams() {
        return new TSPTaskAddRequest();
    }

    @Override
    protected void process(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        TSPTaskAddRequest req = getPostParameters(request, TSPTaskAddRequest.class);

        TSPTask task = new TSPTask();
        task.setName(req.getName());
        task.setProblem(problemDao.getRef(req.getProblemId()));
        task.setCreated(new Date());
        Key<TSPTask> taskKey = taskDao.save(task);

        IdResponse respId = new IdResponse();
        respId.setId(taskKey.getId());
        jsonResponse(response, respId);
    }
}