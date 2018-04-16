package clc3.common;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskHandle;
import com.google.appengine.api.taskqueue.TaskOptions;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public abstract class AbstractQueue {

    private final static String WORKER_ENDPOINT = "process";

    public abstract String getQueueName();
    public String getWorkerEndpoint() { return WORKER_ENDPOINT; }

    private TaskOptions getTaskOptions() {
        return TaskOptions.Builder.withUrl("/" + getWorkerEndpoint());
    }

    public String addTask(Object task) {
        TaskOptions options = getTaskOptions();
        Class clazz = task.getClass();
        while (clazz != Object.class) {
            for (Field f: clazz.getDeclaredFields()) {
                if (!Modifier.isTransient(f.getModifiers())) {
                    String name = f.getName();
                    try {
                        f.setAccessible(true);
                        // map params
                        options = options.param(name, f.get(task).toString());
                    } catch (IllegalAccessException ex) { }
                }
            }
            clazz = clazz.getSuperclass();
        }
        return addToQueue(options);
    }

    public String addTask(String name, Object value) {
        TaskOptions options = getTaskOptions();
        return addToQueue(options.param(name, value.toString()));
    }

    private String addToQueue(TaskOptions options) {
        Queue queue = QueueFactory.getQueue(getQueueName());
        return queue.add(options).getName();
    }

    public void removeTask(String queueTaskName) {
        Queue queue = QueueFactory.getQueue(getQueueName());
        queue.deleteTask(queueTaskName);
    }

    public void removeAllTasks() {
        Queue queue = QueueFactory.getQueue(getQueueName());
        queue.purge();
    }
}
