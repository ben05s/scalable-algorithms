package clc3.webapi.mc;

import clc3.common.servlets.requests.IdRequest;
import clc3.montecarlo.database.daos.*;
import clc3.webapi.mc.responses.*;
import clc3.montecarlo.database.entities.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.collect.Lists;

import java.io.IOException;
import java.util.*;

@WebServlet(urlPatterns = "/mc/task")
public class MCTaskServlet extends BaseServlet {

    private MCTaskDao taskDao = serviceLocator.getMcTaskDao();

    @Override
    protected Object createEmptyParams() {
        return new IdRequest();
    }

    @Override
    protected void process(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        IdRequest req = (IdRequest) getParameters(request);
        
        if (req.getId() != null) {
            MCTask task = taskDao.getById(req.getId());
            jsonResponse(response, new MCTaskResponse(task));
        } else {
            List<MCTaskResponse> resp = new ArrayList<>();
            List<MCTask> tasks = taskDao.getAllOrderByCreated();
            for (MCTask task : tasks) {
                MCTaskResponse entry = new MCTaskResponse(task);
                resp.add(entry);
            }
            jsonResponse(response, Lists.reverse(resp));
        }
    }
}