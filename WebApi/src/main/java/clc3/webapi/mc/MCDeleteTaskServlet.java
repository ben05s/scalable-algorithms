package clc3.webapi.mc;

import clc3.common.servlets.requests.IdRequest;
import clc3.montecarlo.database.daos.*;
import clc3.montecarlo.database.entities.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

@WebServlet(urlPatterns = "/mc/task/delete")
public class MCDeleteTaskServlet extends BaseServlet {

    private MCTaskDao taskDao = serviceLocator.getMcTaskDao();
    private MCTaskIterationDao taskIterationDao = serviceLocator.getMcTaskIterationDao();
    private MCTaskResultDao taskResultDao = serviceLocator.getMcTaskResultDao();

    @Override
    protected Object createEmptyParams() {
        return new IdRequest();
    }

    @Override
    protected void process(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        IdRequest req = (IdRequest)getParameters(request);
        
        if(req.getId() != null) {
            MCTask task = taskDao.getById(req.getId());
            if(task != null) taskDao.delete(task);

            taskIterationDao.deleteAllByTask(MCTask.key(req.getId()));
            taskResultDao.deleteAllByTask(MCTask.key(req.getId()));

            jsonResponse(response, task.getId());
        }
    }
}