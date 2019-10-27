package servlet;

import com.google.gson.Gson;
import model.DailyReport;
import service.DailyReportService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class NewDayServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Gson gson = new Gson();
        String json = gson.toJson(DailyReportService.getInstance().doReport(), DailyReport.class);
        resp.getWriter().write(json);
        resp.setStatus(200);
    }
}
