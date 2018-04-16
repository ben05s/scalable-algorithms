package clc3.tsp.servlets;

import clc3.tsp.database.daos.TSPTaskConfigRunDao;
import clc3.tsp.database.daos.TSPTaskConfigRunIterationDao;
import clc3.tsp.database.daos.TSPTaskDao;
import clc3.tsp.database.entities.*;
import clc3.tsp.database.enums.TaskStatus;
import clc3.tsp.implementation.TSP;
import clc3.tsp.implementation.TSPOperatorManager;
import clc3.tsp.interfaces.GeneticAlgorithm;
import clc3.tsp.interfaces.Individual;
import clc3.tsp.servlets.requests.TSPProcessRequest;
import com.google.common.primitives.Ints;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/process")
public class ProcessServlet extends BaseServlet {
    private static final Logger log = Logger.getLogger(ProcessServlet.class.getName());

    private TSPOperatorManager tspOperatorManager = serviceLocator.getTspOperatorManager();
    private TSPTaskDao taskDao = serviceLocator.getTspTaskDao();
    private TSPTaskConfigRunDao taskConfigRunDao = serviceLocator.getTspTaskConfigRunDao();
    private TSPTaskConfigRunIterationDao taskConfigRunIterationDao = serviceLocator.getTspTaskConfigRunIterationDao();

    private double lastFitness = Double.MAX_VALUE;

    @Override
    protected Object createEmptyParams() {
        return new TSPProcessRequest();
    }

    @Override
    protected void process(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        TSPProcessRequest req = (TSPProcessRequest)getParameters(request);

        TSPTaskConfigRun run = taskConfigRunDao.getById(req.getId());
        long taskId = run.getTask().getId();
        TSPTaskConfig config = run.getConfig();
        TSPProblem problem = run.getProblem();

        log.info("Travelling salesman problem worker is processing a task");
        GeneticAlgorithm ga = new GeneticAlgorithm();
        ga.setSelection(tspOperatorManager.getSelection(config.getSelection()));
        ga.setCrossover(tspOperatorManager.getCrossover(config.getCrossover()));
        ga.setMutation(tspOperatorManager.getMutation(config.getMutation()));
        ga.setElitism(config.isElitism());
        ga.setPopulationSize(config.getPopulationSize());
        ga.setMutationRate(config.getMutationRate());
        ga.setIterations(config.getIterations());

        Collection<clc3.tsp.implementation.City> gaCities = new ArrayList<>();
        for (TSPCity dbCity : problem.getCities()) {
            gaCities.add(new clc3.tsp.implementation.City(dbCity.getX(), dbCity.getY()));
        }
        ga.setProblem(new TSP(gaCities.toArray(new clc3.tsp.implementation.City[gaCities.size()])));


        ga.setCallback((int iteration, Individual individual) -> {
            // save iteration
            TSPTaskConfigRunIteration runIteration = new TSPTaskConfigRunIteration();
            runIteration.setTaskConfigRun(taskConfigRunDao.getRef(run.getId()));
            runIteration.setIteration(iteration);
            runIteration.setFitness(individual.getFitness());
            List<Integer> tour = Ints.asList((int[])individual.getData());
            runIteration.setTour(tour);
            Key<TSPTaskConfigRunIteration> runIterationKey = taskConfigRunIterationDao.save(runIteration);
            Ref<TSPTaskConfigRunIteration> runIterationRef = taskConfigRunIterationDao.getRef(runIterationKey.getId());

            if (lastFitness > individual.getFitness()) {
                lastFitness = individual.getFitness();
                taskDao.clearCache();
                TSPTask task = taskDao.getById(taskId);
                TSPTaskConfigRunIteration taskBestIteration = task.getBestIteration();
                if (taskBestIteration != null) {
                    double iterFitness = taskBestIteration.getFitness();
                    if (iterFitness <= 0) {
                        iterFitness = Double.MAX_VALUE;
                    }
                    if (iterFitness > lastFitness) {
                        task.setBestIteration(runIterationRef);
                        taskDao.save(task);
                    }
                } else {
                    task.setBestIteration(runIterationRef);
                    taskDao.save(task);
                }
            }
            run.setLastIteration(runIterationRef);
            taskConfigRunDao.save(run);
        });

        run.setStatus(TaskStatus.STARTED);
        run.setStarted(new Date());
        taskConfigRunDao.save(run);

        ga.execute();

        run.setStatus(TaskStatus.DONE);
        run.setCompleted(new Date());
        taskConfigRunDao.save(run);

        response.setStatus(HttpServletResponse.SC_OK);
    }
}