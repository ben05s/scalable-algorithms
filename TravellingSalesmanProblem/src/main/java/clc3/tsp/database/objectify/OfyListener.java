package clc3.tsp.database.objectify;

import clc3.tsp.database.entities.*;
import com.googlecode.objectify.ObjectifyService;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class OfyListener implements ServletContextListener {
    public void contextInitialized(ServletContextEvent event) {
        ObjectifyService.register(TSPProblem.class);
        ObjectifyService.register(TSPTask.class);
        ObjectifyService.register(TSPTaskConfig.class);
        ObjectifyService.register(TSPTaskConfigRun.class);
        ObjectifyService.register(TSPTaskConfigRunIteration.class);
    }

    public void contextDestroyed(ServletContextEvent event) {
        // App Engine does not currently invoke this method.
    }
}