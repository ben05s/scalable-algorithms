package clc3.webapi.tsp.servlets.task;

import clc3.common.servlets.requests.IdRequest;
import clc3.tsp.database.daos.TSPTaskConfigRunDao;
import clc3.tsp.database.daos.TSPTaskDao;
import clc3.tsp.database.entities.TSPTaskConfigRun;
import clc3.tsp.queues.TSPQueue;
import clc3.webapi.tsp.utils.IdRequestServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/tsp/task/start")
public class TSPTaskStartServlet extends IdRequestServlet {
    private static final String TSP_ID = "id";

    private TSPQueue queue = serviceLocator.getTspQueue();
    private TSPTaskDao taskDao = serviceLocator.getTspTaskDao();
    private TSPTaskConfigRunDao taskConfigRunDao = serviceLocator.getTspTaskConfigRunDao();

    @Override // adds the runs to the queue
    protected void process(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        IdRequest req = (IdRequest)getParameters(request);
        for (TSPTaskConfigRun run : taskConfigRunDao.getByTask(taskDao.getKey(req.getId()))) {
            queue.addTask(TSP_ID, run.getId());
        }
        response.setStatus(HttpServletResponse.SC_OK);
    }
}