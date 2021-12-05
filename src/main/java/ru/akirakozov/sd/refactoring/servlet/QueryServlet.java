package main.java.ru.akirakozov.sd.refactoring.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author akirakozov
 */
public class QueryServlet extends Servlet {
//    @Override
//    protected void makeRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        String command = request.getParameter("command");
//
//        if ("max".equals(command)) {
//            try {
//                try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
//                    Statement stmt = c.createStatement();
//                    ResultSet rs = stmt.executeQuery("SELECT * FROM PRODUCT ORDER BY PRICE DESC LIMIT 1");
//                    response.getWriter().println("<html><body>");
//                    response.getWriter().println("<h1>Product with max price: </h1>");
//
//                    while (rs.next()) {
//                        String  name = rs.getString("name");
//                        int price  = rs.getInt("price");
//                        response.getWriter().println(name + "\t" + price + "</br>");
//                    }
//                    response.getWriter().println("</body></html>");
//
//                    rs.close();
//                    stmt.close();
//                }
//
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//        } else if ("min".equals(command)) {
//            try {
//                try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
//                    Statement stmt = c.createStatement();
//                    ResultSet rs = stmt.executeQuery("SELECT * FROM PRODUCT ORDER BY PRICE LIMIT 1");
//                    response.getWriter().println("<html><body>");
//                    response.getWriter().println("<h1>Product with min price: </h1>");
//
//                    while (rs.next()) {
//                        String  name = rs.getString("name");
//                        int price  = rs.getInt("price");
//                        response.getWriter().println(name + "\t" + price + "</br>");
//                    }
//                    response.getWriter().println("</body></html>");
//
//                    rs.close();
//                    stmt.close();
//                }
//
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//        } else if ("sum".equals(command)) {
//            try {
//                try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
//                    Statement stmt = c.createStatement();
//                    ResultSet rs = stmt.executeQuery("SELECT SUM(price) FROM PRODUCT");
//                    response.getWriter().println("<html><body>");
//                    response.getWriter().println("Summary price: ");
//
//                    if (rs.next()) {
//                        response.getWriter().println(rs.getInt(1));
//                    }
//                    response.getWriter().println("</body></html>");
//
//                    rs.close();
//                    stmt.close();
//                }
//
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//        } else if ("count".equals(command)) {
//            try {
//                try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
//                    Statement stmt = c.createStatement();
//                    ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM PRODUCT");
//                    response.getWriter().println("<html><body>");
//                    response.getWriter().println("Number of products: ");
//
//                    if (rs.next()) {
//                        response.getWriter().println(rs.getInt(1));
//                    }
//                    response.getWriter().println("</body></html>");
//
//                    rs.close();
//                    stmt.close();
//                }
//
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//        } else {
//            response.getWriter().println("Unknown command: " + command);
//        }
//
//        response.setContentType("text/html");
//        response.setStatus(HttpServletResponse.SC_OK);
//    }

    protected void makeRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String command = request.getParameter("command");

        switch (command) {
            case "sum":
                countSum(response);
                break;
            case "max":
                countMax(response);
                break;
            case "min":
                countMin(response);
                break;
            case "count":
                count(response);
                break;
            default:
                response.getWriter().println("Unknown command: " + command);
        }
    }

    private void countMax(HttpServletResponse response) {
        try {
            try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
                Statement stmt = c.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM PRODUCT ORDER BY PRICE DESC LIMIT 1");
                List<String> info = new ArrayList<>();

                info.add("<h1>Product with max price: </h1>");

                while (rs.next()) {
                    String name = rs.getString("name");
                    int price = rs.getInt("price");
                    info.add(name + "\t" + price + "</br>");
                }

                rs.close();
                stmt.close();

                addHttpInfo(response, info);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void countSum(HttpServletResponse response) {
        try {
            try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
                Statement stmt = c.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT SUM(price) FROM PRODUCT");
                List<String> info = new ArrayList<>();

                info.add("Summary price: ");

                if (rs.next()) {
                    info.add(rs.getString(1));
                }

                rs.close();
                stmt.close();

                addHttpInfo(response, info);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void countMin(HttpServletResponse response) {
        try {
            try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
                Statement stmt = c.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM PRODUCT ORDER BY PRICE LIMIT 1");
                List<String> info = new ArrayList<>();

                info.add("<h1>Product with min price: </h1>");

                while (rs.next()) {
                    String  name = rs.getString("name");
                    int price  = rs.getInt("price");
                    info.add(name + "\t" + price + "</br>");
                }

                rs.close();
                stmt.close();

                addHttpInfo(response, info);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void count(HttpServletResponse response) {
        try {
            try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
                Statement stmt = c.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM PRODUCT");
                List<String> info = new ArrayList<>();

                info.add("Number of products: ");

                if (rs.next()) {
                    info.add(rs.getString(1));
                }

                rs.close();
                stmt.close();

                addHttpInfo(response, info);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}


