package main.java.ru.akirakozov.sd.refactoring.servlet;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.List;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


abstract class Servlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

        try {
            makeRequest(request, response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        response.setContentType("test/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    protected abstract void makeRequest(HttpServletRequest request, HttpServletResponse response) throws IOException;

    protected void addHttpInfo(HttpServletResponse response, List<String> info) throws IOException {
        response.getWriter().println("<html><body>");
        info.forEach(s -> {
            try {
                response.getWriter().println(s);
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        });
        response.getWriter().println("</body></html>");
    }
}
