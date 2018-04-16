package clc3.webapi.mc;

import clc3.montecarlo.database.entities.*;
import clc3.montecarlo.database.enums.TaskStatus;
import clc3.common.servlets.requests.IdRequest;
import clc3.montecarlo.database.daos.*;
import clc3.montecarlo.queues.MonteCarloQueue;
import clc3.webapi.mc.responses.MCTaskResponse;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = "/mc/task/dequeue")
public class MCDequeueServlet extends BaseServlet {

    private MonteCarloQueue monteCarloQueue = serviceLocator.getMonteCarloQueue();
    private MCTaskDao taskDao = serviceLocator.getMcTaskDao();

    @Override
    protected Object createEmptyParams() {
        return new IdRequest();
    }

    @Override
    protected void process(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        IdRequest req = (IdRequest)getParameters(request);

        MCTask task = taskDao.getById(req.getId());

        if(task != null) {
            List<String> queueTaskNames = task.getQueueTaskNames();
            if(queueTaskNames != null) { 
                for(String queueTaskName: queueTaskNames) {
                    monteCarloQueue.removeTask(queueTaskName);
                }
            }
            task.setQueueTaskNames(new ArrayList<String>());
            task.setStatus(TaskStatus.STOPPED);
            taskDao.save(task);

            jsonResponse(response, new MCTaskResponse(task));
        }
    }
}