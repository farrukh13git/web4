package servlet;

import com.google.gson.Gson;
import service.CarService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Gson gson = new Gson();
        String json = gson.toJson(CarService.getInstance().getAllCars());
        resp.getWriter().write(json);
        resp.setStatus(200);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        String licensePlate = req.getParameter("licensePlate");
        if (!CarService.getInstance().buyCar(licensePlate)) {
            resp.setStatus(400);
        }
        resp.setStatus(200);
    }
}
