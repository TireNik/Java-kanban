package server;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import manager.Managers;
import manager.TaskManager;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;

public class HttpTaskServer {
    private final HttpServer httpServer;
    private final TaskManager taskManager;
    private static final int PORT = 8080;

    public HttpTaskServer(TaskManager taskManager) throws IOException {
        this.httpServer = HttpServer.create(new InetSocketAddress(PORT), 0);
        this.taskManager = taskManager;

        httpServer.createContext("/tasks", new TaskHandler(taskManager));
        httpServer.createContext("/Subtasks", new SubtaskHandler(taskManager));
//        httpServer.createContext("/stop", stop());

        httpServer.setExecutor(null);
    }

    public void start() {
        httpServer.start();
        System.out.println("Server start");
    }

    public HttpHandler stop () {
        httpServer.stop(0);
        System.out.println("Server stop");
        return null;
    }

    public static void main(String[] args) throws IOException {
        File file = new File("/Users/sviridovnikita/IdeaProjects/java-kanban/fileBacked/tasks.csv");
        TaskManager taskManager = Managers.getFileBackedTaskManager(file);

        HttpTaskServer server = new HttpTaskServer(taskManager);
        server.start();

//        System.out.println("Загруженные задачи: " + taskManager.getTaskById(1));

    }
}
