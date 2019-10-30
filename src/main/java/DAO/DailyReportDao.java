package DAO;

import model.DailyReport;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class DailyReportDao {

    private Session session;

    public DailyReportDao(Session session) {
        this.session = session;
    }
    
    @SuppressWarnings("unchecked")
    public List<DailyReport> getAllDailyReport() {
        Transaction transaction = null;
        List<DailyReport> dailyReports;
        try {
            transaction = session.beginTransaction();
            dailyReports = session.createQuery("FROM DailyReport").list();
            transaction.commit();
            dailyReports.remove(dailyReports.size() - 1);
        } catch (Exception e) {
            dailyReports = new ArrayList<>();
            if (transaction != null) {
                try {
                    transaction.rollback();
                } catch (Exception ex) {
                }
            }
        } finally {
            try {
                session.close();
            } catch (Exception e) {
            }
        }
        return dailyReports;
    }

    public void addSoldCarReport(Long price, Long id) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Query query = session.createQuery("UPDATE DailyReport d SET d.earnings = d.earnings + :price, d.soldCars = d.soldCars + 1 WHERE d.id = :lastId");
            query.setParameter("price", price);
            query.setParameter("lastId", id);
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                try {
                    transaction.rollback();
                } catch (Exception ex) {
                }
            }
        } finally {
            try {
                session.close();
            } catch (Exception e) {
            }
        }
    }

    public Long getLastId() {
        Transaction transaction = null;
        Long id = null;
        try {
            transaction = session.beginTransaction();
            id = (Long) session.createQuery("SELECT max(d.id) FROM DailyReport d").uniqueResult();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                try {
                    transaction.rollback();
                } catch (Exception ex) {
                }
            }
        } finally {
            try {
                session.close();
            } catch (Exception e) {
            }
        }
        return id;
    }

    public void addNewReport() {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            DailyReport dailyReport = new DailyReport(0L, 0L);
            session.save(dailyReport);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                try {
                    transaction.rollback();
                } catch (Exception ex) {
                }
            }
        } finally {
            try {
                session.close();
            } catch (Exception e) {
            }
        }
    }

    public DailyReport getReport(Long id) {
        Transaction transaction = null;
        DailyReport dailyReport;
        try {
            transaction = session.beginTransaction();
            dailyReport = (DailyReport) session.get(DailyReport.class, id);
            transaction.commit();
        } catch (Exception e) {
            dailyReport = new DailyReport();
            if (transaction != null) {
                try {
                    transaction.rollback();
                } catch (Exception ex) {
                }
            }
        } finally {
            try {
                session.close();
            } catch (Exception e) {
            }
        }
        return dailyReport;
    }

    public void dropTable() {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.createQuery("DELETE FROM DailyReport").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                try {
                    transaction.rollback();
                } catch (Exception ex) {
                }
            }
        } finally {
            try {
                session.close();
            } catch (Exception e) {
            }
        }
    }
}
