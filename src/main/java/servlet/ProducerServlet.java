package servlet;

import service.CarService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ProducerServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        String brand = req.getParameter("brand");
        String model = req.getParameter("model");
        String licensePlate = req.getParameter("licensePlate");
        String priceString = req.getParameter("price");
        Long price = Long.parseLong(priceString);

        if (!CarService.getInstance().addCar(brand, model, licensePlate, price)) {
            resp.setStatus(403);
        }
        resp.setStatus(200);
    }
}
