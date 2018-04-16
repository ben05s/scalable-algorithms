package clc3.webapi.tsp.servlets.task;

import clc3.common.servlet.responses.IdResponse;
import clc3.common.servlets.requests.IdRequest;
import clc3.tsp.database.daos.*;
import clc3.tsp.database.entities.TSPTask;
import clc3.tsp.database.entities.TSPTaskConfig;
import clc3.tsp.database.entities.TSPTaskConfigRun;
import clc3.tsp.database.entities.TSPTaskConfigRunIteration;
import clc3.tsp.queues.TSPDeleteQueue;
import clc3.tsp.queues.TSPQueue;
import clc3.webapi.tsp.requests.TSPTaskAddRequest;
import clc3.webapi.tsp.utils.IdRequestServlet;
import com.googlecode.objectify.Key;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@WebServlet(urlPatterns = "/tsp/task/delete")
public class TSPTaskDeleteServlet extends IdRequestServlet {

    private TSPDeleteQueue queue = serviceLocator.getTspDeleteQueue();

    TSPTaskDao taskDao = serviceLocator.getTspTaskDao();
    TSPTaskConfigDao taskConfigDao = serviceLocator.getTspTaskConfigDao();

    @Override
    protected void process(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        IdRequest req = (IdRequest)getParameters(request);
        Key<TSPTask> taskKey = taskDao.getKey(req.getId());
        for (TSPTaskConfig config : taskConfigDao.getByTaskKey(taskKey)) {
            queue.addTask("id", config.getId());
        }
        taskDao.delete(taskDao.getByKey(taskKey));
        response.setStatus(HttpServletResponse.SC_OK);
    }
}