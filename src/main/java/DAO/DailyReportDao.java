package DAO;

import model.DailyReport;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class DailyReportDao {

    private Session session;

    public DailyReportDao(Session session) {
        this.session = session;
    }

    public List<DailyReport> getAllDailyReport() {
        Transaction transaction = session.beginTransaction();
        List<DailyReport> dailyReports = session.createQuery("FROM DailyReport").list();
        transaction.commit();
        session.close();
        return dailyReports;
    }

    public void addSoldCarReport(Long price, Long id) {
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("UPDATE DailyReport d SET d.earnings = d.earnings + :price, d.soldCars = d.soldCars + 1 WHERE d.id = :lastId");
        query.setParameter("price", price);
        query.setParameter("lastId", id);
        query.executeUpdate();
        transaction.commit();
        session.close();
    }

    public Long getLastId() {
        Transaction transaction = session.beginTransaction();
        Long id = (Long) session.createQuery("SELECT max(d.id) FROM DailyReport d").uniqueResult();
        transaction.commit();
        session.close();
        return id;
    }

    public void addNewReport() {
        Transaction transaction = session.beginTransaction();
        DailyReport dailyReport = new DailyReport(0L, 0L);
        session.save(dailyReport);
        transaction.commit();
        session.close();
    }

    public DailyReport getReport(Long id) {
        session.beginTransaction();
        DailyReport dailyReport = (DailyReport) session.get(DailyReport.class, id);
        session.getTransaction().commit();
        session.close();
        return dailyReport;
    }

    public void dropTable() {
        Transaction transaction = session.beginTransaction();
        session.createQuery("DELETE FROM DailyReport").executeUpdate();
        transaction.commit();
        session.close();
    }
}
