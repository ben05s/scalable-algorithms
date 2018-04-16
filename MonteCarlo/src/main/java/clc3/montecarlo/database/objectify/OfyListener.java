package clc3.montecarlo.database.objectify;

import clc3.montecarlo.database.entities.MCTask;
import clc3.montecarlo.database.entities.MCTaskIteration;
import clc3.montecarlo.database.entities.MCTaskResult;

import com.googlecode.objectify.ObjectifyService;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class OfyListener implements ServletContextListener {
    public void contextInitialized(ServletContextEvent event) {
        ObjectifyService.register(MCTask.class);
        ObjectifyService.register(MCTaskIteration.class);
        ObjectifyService.register(MCTaskResult.class);
    }

    public void contextDestroyed(ServletContextEvent event) {
        // App Engine does not currently invoke this method.
    }
}