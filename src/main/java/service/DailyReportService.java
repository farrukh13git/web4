package service;

import DAO.DailyReportDao;
import model.DailyReport;
import org.hibernate.SessionFactory;
import util.DBHelper;

import java.util.List;

public class DailyReportService {

    private static DailyReportService dailyReportService;

    private SessionFactory sessionFactory;

    private DailyReportService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public static DailyReportService getInstance() {
        if (dailyReportService == null) {
            dailyReportService = new DailyReportService(DBHelper.getSessionFactory());
        }
        return dailyReportService;
    }

    public List<DailyReport> getAllDailyReports() {
        return getDailyReportDao().getAllDailyReport();
    }


    public DailyReport getLastReport() {
        return getDailyReportDao().getReport(getDailyReportDao().getLastId() - 1);
    }

    public DailyReport doReport() {
        getDailyReportDao().addNewReport();
        return getDailyReportDao().getReport(getDailyReportDao().getLastId() - 1);
    }

    public void cleanUp() {
        getDailyReportDao().dropTable();
    }

    private DailyReportDao getDailyReportDao() {
        return new DailyReportDao(sessionFactory.openSession());
    }
}
