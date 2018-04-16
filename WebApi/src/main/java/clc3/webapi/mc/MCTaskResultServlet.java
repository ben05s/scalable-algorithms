package clc3.webapi.mc;
import clc3.common.servlets.requests.*;
import clc3.montecarlo.database.daos.*;
import clc3.montecarlo.database.entities.*;
import clc3.montecarlo.database.enums.TaskStatus;
import clc3.montecarlo.util.MCTaskResultAggregator;
import clc3.webapi.mc.responses.MCTaskResultResponse;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@WebServlet(urlPatterns = "/mc/task/result")
public class MCTaskResultServlet extends BaseServlet {

    private MCTaskDao taskDao = serviceLocator.getMcTaskDao();
    private MCTaskResultDao taskResultDao = serviceLocator.getMcTaskResultDao();

    @Override
    protected Object createEmptyParams() {
        return new IdRequest();
    }

    @Override
    protected void process(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        IdRequest req = (IdRequest)getParameters(request);

        MCTask task = taskDao.getById(req.getId());
        
        MCTaskResult aggregatedTaskResult = null;
        
        List<MCTaskResult> taskResults = taskResultDao.getByMcTaskKey(task.getKey());
        if(taskResults.size() > 1){
            taskResultDao.deleteBulk(taskResults);

            aggregatedTaskResult = MCTaskResultAggregator.aggregateTaskResults(taskResults);
            aggregatedTaskResult.setMcTask(task.getKey());
            taskResultDao.save(aggregatedTaskResult);
        } else if(taskResults.size() == 1) {
            aggregatedTaskResult = taskResults.get(0);
        }

        if(aggregatedTaskResult != null) {
            if(task.getStatus() != TaskStatus.DONE 
                && aggregatedTaskResult.getCalculatedIterations() == task.getIterations()) {
                task.setStatus(TaskStatus.DONE);
                task.setCompleted(new Date());
                long duration = aggregatedTaskResult.getComputeTimes().stream().mapToInt(Long::intValue).sum();
                task.setDuration(duration);
                taskDao.save(task);
            }

            jsonResponse(response, new MCTaskResultResponse(aggregatedTaskResult));        
        } else {
            jsonResponse(response, new MCTaskResultResponse());                
        }
    }
}