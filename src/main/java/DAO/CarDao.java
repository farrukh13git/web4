package DAO;

import model.Car;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class CarDao {

    private Session session;

    public CarDao(Session session) {
        this.session = session;
    }

    public List<Car> getAllCars() {
        Transaction transaction = session.beginTransaction();
        List<Car> list = session.createQuery("FROM Car").list();
        transaction.commit();
        session.close();
        return list;
    }

    public void addCar(String brand, String model, String licensePlate, Long price) {
        Transaction transaction = session.beginTransaction();
        Car car = new Car(brand, model, licensePlate, price);
        session.save(car);
        transaction.commit();
        session.close();
    }

    public Long thisBrandCount(String brand) {
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("SELECT count(*) FROM Car c WHERE c.brand = :br");
        query.setParameter("br", brand);
        Long count = (Long) query.uniqueResult();
        transaction.commit();
        session.close();
        return count;
    }

    public Car getCar(String licensePlate) {
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("FROM Car c WHERE c.licensePlate = :licenseP");
        query.setParameter("licenseP", licensePlate);
        Car car = (Car) query.uniqueResult();
        transaction.commit();
        session.close();
        return car;
    }

    public  boolean isExistsCar(String licensePlate) {
        return getCar(licensePlate) != null;
    }

    public Long getPrice(String licensePlate) {
        return getCar(licensePlate).getPrice();
    }

    public void deleteCar(Car car) {
        Transaction transaction = session.beginTransaction();
        session.delete(car);
        transaction.commit();
        session.close();
    }

    public void dropTable() {
        Transaction transaction = session.beginTransaction();
        session.createQuery("DELETE FROM Car").executeUpdate();
        transaction.commit();
        session.close();
    }
}
