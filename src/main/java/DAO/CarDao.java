package DAO;

import model.Car;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class CarDao {

    private Session session;

    public CarDao(Session session) {
        this.session = session;
    }

    public List<Car> getAllCars() {
        Transaction transaction = null;
        List<Car> list;
        try {
            transaction = session.beginTransaction();
            list = session.createQuery("FROM Car").list();
            transaction.commit();
        } catch (Exception e) {
            list = new ArrayList<>();
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
        return list;
    }

    public void addCar(String brand, String model, String licensePlate, Long price) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Car car = new Car(brand, model, licensePlate, price);
            session.save(car);
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

    public Long thisBrandCount(String brand) {
        Transaction transaction = null;
        Long count = null;
        try {
            transaction = session.beginTransaction();
            Query query = session.createQuery("SELECT count(*) FROM Car c WHERE c.brand = :br");
            query.setParameter("br", brand);
            count = (Long) query.uniqueResult();
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
        return count;
    }

    public Car getCar(String licensePlate) {
        Transaction transaction = null;
        Car car;
        try {
            transaction = session.beginTransaction();
            Query query = session.createQuery("FROM Car c WHERE c.licensePlate = :licenseP");
            query.setParameter("licenseP", licensePlate);
            car = (Car) query.uniqueResult();
            transaction.commit();
        } catch (Exception e) {
            car = new Car();
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
        return car;
    }

    public boolean isExistsCar(String licensePlate) {
        return getCar(licensePlate) != null;
    }

    public Long getPrice(String licensePlate) {
        return getCar(licensePlate).getPrice();
    }

    public void deleteCar(Car car) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.delete(car);
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

    public void dropTable() {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.createQuery("DELETE FROM Car").executeUpdate();
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
