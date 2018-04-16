package clc3.webapi.mc;

import clc3.montecarlo.database.entities.*;
import clc3.montecarlo.database.enums.TaskStatus;
import clc3.common.servlets.requests.IdRequest;
import clc3.montecarlo.database.daos.*;
import clc3.montecarlo.queues.MonteCarloQueue;
import clc3.montecarlo.servlets.requests.*;
import clc3.webapi.mc.responses.MCTaskResponse;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.math.IntMath;

import java.io.IOException;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = "/mc/task/enqueue")
public class MCEnqueueServlet extends BaseServlet {

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
            List<String> queueTaskNames = new ArrayList<>();
            if((task.getConcurrentWorkers() > task.getIterations()) || task.getConcurrentWorkers() < 1) {
                task.setConcurrentWorkers(1);
            }

            int stepSize = IntMath.divide(task.getIterations(), task.getConcurrentWorkers(), RoundingMode.CEILING);
            // divide iterations / steps and queue each subrange 
            // for parallel computation and to allow dynamic scaling
            for(int i = 0; i < task.getIterations(); i = i + stepSize) {
                int from = i;
                int to = ((i + stepSize) > task.getIterations()) ? task.getIterations() : (i + stepSize);

                MCQueueRequest queueReq = new MCQueueRequest(task.getId(), new Long(from), new Long(to));
                String queueTaskName = monteCarloQueue.addTask(queueReq);
                queueTaskNames.add(queueTaskName);
            }

            task.setQueueTaskNames(queueTaskNames);
            task.setStatus(TaskStatus.QUEUED);
            taskDao.save(task);

            jsonResponse(response, new MCTaskResponse(task));
        }
    }
}