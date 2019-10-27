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
        return getDRDao().getAllDailyReport();
    }


    public DailyReport getLastReport() {
        return getDRDao().getReport(getDRDao().getLastId() - 1);
    }

    public DailyReport doReport() {
        getDRDao().addNewReport();
        return getDRDao().getReport(getDRDao().getLastId() - 1);
    }

    public void cleanUp() {
        getDRDao().dropTable();
    }

    private DailyReportDao getDRDao() {
        return new DailyReportDao(sessionFactory.openSession());
    }
}
