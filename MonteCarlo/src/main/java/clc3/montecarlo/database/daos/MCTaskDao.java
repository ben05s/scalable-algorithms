package clc3.montecarlo.database.daos;

import clc3.common.AbstractDao;
import clc3.montecarlo.database.entities.MCTask;
import java.util.*;

import static com.googlecode.objectify.ObjectifyService.ofy;

public class MCTaskDao extends AbstractDao<MCTask> {
    protected Class<MCTask> getEntityType() { return MCTask.class; }

    public List<MCTask> getAllOrderByCreated() {
        return ofy().load().type(getEntityType()).order("created").list();
    }
}