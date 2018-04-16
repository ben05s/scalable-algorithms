package clc3.montecarlo.database.daos;

import clc3.common.AbstractDao;
import clc3.montecarlo.database.entities.MCTask;
import clc3.montecarlo.database.entities.MCTaskResult;

import com.googlecode.objectify.Key;

import java.util.List;
import java.util.stream.IntStream;

import static com.googlecode.objectify.ObjectifyService.ofy;

public class MCTaskResultDao extends AbstractDao<MCTaskResult> {
    protected Class<MCTaskResult> getEntityType() { return MCTaskResult.class; }

    public List<MCTaskResult> getByMcTaskKey(Key<MCTask> mcTask) {
        return ofy().load().type(getEntityType()).filter("mcTask", mcTask).list();
    }

    public void deleteAllByTask(Key<MCTask> mcTask) {
        List<MCTaskResult> list = this.getByMcTaskKey(mcTask);

        int bulkDelete = 400;
        IntStream.range(0, (list.size() + bulkDelete - 1) / bulkDelete)
                .mapToObj(i -> list.subList(i * bulkDelete, Math.min(bulkDelete * (i + 1), list.size())))
                .forEach(l -> this.delete(l));
    }

    public void deleteBulk(List<MCTaskResult> list) {
        int bulkDelete = 400;
        IntStream.range(0, (list.size() + bulkDelete - 1) / bulkDelete)
                .mapToObj(i -> list.subList(i * bulkDelete, Math.min(bulkDelete * (i + 1), list.size())))
                .forEach(l -> this.delete(l));
    }
}