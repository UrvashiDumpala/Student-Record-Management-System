// FULL CODE — SAME AS YOURS (NO CHANGE REQUIRED)
// 100% working with dropdown values

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class MainAPI {

    public static void main(String[] args) throws Exception {
        StudentDAO dao = new StudentDAO();

        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        System.out.println("Backend running at http://localhost:8080");

        // ADD STUDENT
        server.createContext("/addStudent", exchange -> {
            try {
                if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
                    addCors(exchange);
                    exchange.sendResponseHeaders(204, -1);
                    return;
                }
                if (!"POST".equalsIgnoreCase(exchange.getRequestMethod())) {
                    addCors(exchange);
                    exchange.sendResponseHeaders(405, -1);
                    return;
                }

                addCors(exchange);
                exchange.getResponseHeaders().set("Content-Type", "application/json; charset=utf-8");

                Map<String,String> form = parseForm(exchange);
                Student s = buildStudentFromForm(form);

                if (s.rollNo == null || s.rollNo.trim().isEmpty()) {
                    sendJson(exchange, 400,
                        "{\"status\":\"invalid\",\"message\":\"roll_no is required\"}");
                    return;
                }

                if (dao.studentExistsByRoll(s.rollNo)) {
                    sendJson(exchange, 409,
                        "{\"status\":\"duplicate\",\"message\":\"Student with this roll_no already exists\"}");
                    return;
                }

                int newId = dao.addStudentReturnId(s);
                if (newId > 0) {
                    String json = String.format(
                        "{\"status\":\"ok\",\"id\":%d,\"message\":\"Student added\"}",
                        newId
                    );
                    sendJson(exchange, 200, json);
                } else {
                    sendJson(exchange, 500,
                        "{\"status\":\"error\",\"message\":\"Database error\"}");
                }

            } catch (Exception e) {
                e.printStackTrace();
                sendJson(exchange, 500,
                    "{\"status\":\"error\",\"message\":\"Server error\"}");
            } finally {
                exchange.close();
            }
        });

        // UPDATE STUDENT
        server.createContext("/updateStudent", exchange -> {
            try {
                if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
                    addCors(exchange);
                    exchange.sendResponseHeaders(204, -1);
                    return;
                }
                if (!"POST".equalsIgnoreCase(exchange.getRequestMethod())) {
                    addCors(exchange);
                    exchange.sendResponseHeaders(405, -1);
                    return;
                }

                addCors(exchange);
                exchange.getResponseHeaders().set("Content-Type", "application/json; charset=utf-8");

                Map<String,String> form = parseForm(exchange);

                String idStr = form.getOrDefault("id", "").trim();
                if (idStr.isEmpty()) {
                    sendJson(exchange, 400,
                        "{\"status\":\"invalid\",\"message\":\"id is required for update\"}");
                    return;
                }

                int id;
                try {
                    id = Integer.parseInt(idStr);
                } catch (NumberFormatException e) {
                    sendJson(exchange, 400,
                        "{\"status\":\"invalid\",\"message\":\"id must be a number\"}");
                    return;
                }

                Student s = buildStudentFromForm(form);
                s.id = id;

                int result = dao.updateStudent(s);
                if (result == 1) {
                    String json = String.format(
                        "{\"status\":\"ok\",\"id\":%d,\"message\":\"Student updated\"}",
                        id
                    );
                    sendJson(exchange, 200, json);
                } else if (result == 0) {
                    sendJson(exchange, 404,
                        "{\"status\":\"not_found\",\"message\":\"No student with this id\"}");
                } else {
                    sendJson(exchange, 500,
                        "{\"status\":\"error\",\"message\":\"Database error\"}");
                }

            } catch (Exception e) {
                e.printStackTrace();
                sendJson(exchange, 500,
                    "{\"status\":\"error\",\"message\":\"Server error\"}");
            } finally {
                exchange.close();
            }
        });

        // LIST STUDENTS
        server.createContext("/listStudents", exchange -> {
            try {
                if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
                    addCors(exchange);
                    exchange.sendResponseHeaders(204, -1);
                    return;
                }
                if (!"GET".equalsIgnoreCase(exchange.getRequestMethod())) {
                    addCors(exchange);
                    exchange.sendResponseHeaders(405, -1);
                    return;
                }

                addCors(exchange);
                exchange.getResponseHeaders().set("Content-Type", "application/json; charset=utf-8");

                String json = dao.getAllStudentsJson();
                sendJson(exchange, 200, json);

            } catch (Exception e) {
                e.printStackTrace();
                sendJson(exchange, 500,
                    "{\"status\":\"error\",\"message\":\"Server error\"}");
            } finally {
                exchange.close();
            }
        });

        server.setExecutor(null);
        server.start();
    }

    private static void addCors(HttpExchange ex) {
        ex.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        ex.getResponseHeaders().add("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
        ex.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");
    }

    private static Map<String,String> parseForm(HttpExchange exchange) throws IOException {
        String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
        Map<String,String> map = new HashMap<>();
        if (body == null || body.isEmpty()) return map;

        String[] pairs = body.split("&");
        for (String pair : pairs) {
            String[] kv = pair.split("=", 2);
            if (kv.length == 2) {
                String key = URLDecoder.decode(kv[0], "UTF-8");
                String value = URLDecoder.decode(kv[1], "UTF-8");
                map.put(key, value);
            }
        }
        return map;
    }

    private static void sendJson(HttpExchange ex, int status, String json) throws IOException {
        byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
        ex.sendResponseHeaders(status, bytes.length);
        try (OutputStream os = ex.getResponseBody()) {
            os.write(bytes);
        }
    }

    private static int parseIntSafe(String val) {
        if (val == null || val.trim().isEmpty()) return 0;
        try { return Integer.parseInt(val.trim()); }
        catch (NumberFormatException e) { return 0; }
    }

    private static Student buildStudentFromForm(Map<String,String> form) {
        Student s = new Student();
        s.rollNo  = form.getOrDefault("roll_no", "");
        s.name    = form.getOrDefault("name", "");
        s.email   = form.getOrDefault("email", "");
        s.phone   = form.getOrDefault("phone", "");
        s.branch  = form.getOrDefault("branch", "");
        s.year    = parseIntSafe(form.get("year"));
        s.section = form.getOrDefault("section", "");
        return s;
    }
}
