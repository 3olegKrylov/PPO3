package main.java.ru.akirakozov.sd.refactoring.servlet;

import javax.servlet.http.HttpServlet;
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
public class GetProductsServlet extends Servlet {

    @Override
    protected void makeRequest(HttpServletRequest request, HttpServletResponse response) {
        try {
            try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
                Statement stmt = c.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM PRODUCT");
                List<String> info = new ArrayList<>();

                while (rs.next()) {
                    String  name = rs.getString("name");
                    int price  = rs.getInt("price");

//                    response.getWriter().println(name + "\t" + price + "</br>");
                    info.add(name + "\t" + price + "</br>");
                }
                ;

                rs.close();
                stmt.close();
                addHttpInfo(response, info);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
