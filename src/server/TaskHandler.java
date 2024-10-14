package server;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import exeptions.NotFoundException;
import manager.TaskManager;
import tasks.Task;

import java.io.IOException;

public class TaskHandler extends BaseHttpHandler {
    private final TaskManager taskManager;
    private final Gson gson = new Gson();

    public TaskHandler(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            String method = exchange.getRequestMethod();
            switch (method) {
                case "GET" -> handleGet(exchange);
                case "POST" -> handelPost(exchange);
                case "DELETE" -> handleDelete(exchange);
                default -> sendNotFound(exchange);
            }
        } catch (Exception e) {
            sendText(exchange, "Internal Server Error", 500);
        }
    }

    private void handleGet (HttpExchange exchange) throws IOException {
        try {
            String query = exchange.getRequestURI().getRawQuery();
            if (query != null && query.startsWith("id=")) {
                String idStr = query.split("=")[1];
                int id = Integer.parseInt(idStr);

                Task task = taskManager.getTaskById(id);

                String response = gson.toJson(task);
                System.out.println(response);
                sendText(exchange, response, 200);
            }
        } catch (NotFoundException e) {
            sendNotFound(exchange);
        } catch (Exception e) {
            e.printStackTrace();
            sendText(exchange, "Internal Server Error", 500);
        }
    }


    private void handelPost (HttpExchange exchange) throws IOException {

    }

    private void handleDelete (HttpExchange exchange) throws IOException {

    }

}
