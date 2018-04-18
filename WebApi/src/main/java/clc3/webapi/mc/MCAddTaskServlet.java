package clc3.webapi.mc;

import clc3.montecarlo.database.entities.*;
import clc3.montecarlo.database.daos.*;
import clc3.webapi.mc.requests.MCAddTaskRequest;
import clc3.webapi.mc.responses.MCTaskResponse;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.*;

@WebServlet(urlPatterns = "/mc/task/add")
public class MCAddTaskServlet extends BaseServlet {

    private MCTaskDao taskDao = serviceLocator.getMcTaskDao();

    @Override
    protected Object createEmptyParams() {
        return new MCAddTaskRequest();
    }

    @Override
    protected void process(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        MCAddTaskRequest req = (MCAddTaskRequest)getPostParameters(request, MCAddTaskRequest.class);
        
        MCTask mc = new MCTask();
        mc.setName(req.getName());
        mc.setIterations(req.getIterations());
        mc.setConcurrentWorkers(req.getConcurrentWorkers());
        mc.setMCSettings(req.getMCSettings());
        mc.setCreated(new Date());
        mc.setQueueTaskNames(new ArrayList<String>());

        taskDao.save(mc);
        
        jsonResponse(response, new MCTaskResponse(mc));
    }
}