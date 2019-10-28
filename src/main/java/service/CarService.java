package service;

import DAO.CarDao;
import DAO.DailyReportDao;
import model.Car;
import org.hibernate.SessionFactory;
import util.DBHelper;

import java.util.List;

public class CarService {

    private static CarService carService;

    private SessionFactory sessionFactory;

    private CarService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public static CarService getInstance() {
        if (carService == null) {
            carService = new CarService(DBHelper.getSessionFactory());
        }
        return carService;
    }

    public List<Car> getAllCars() {
        return getCarDao().getAllCars();
    }

    public boolean addCar(String brand, String model, String licensePlate, Long price) {
        if (!getCarDao().isExistsCar(licensePlate) &&
                getCarDao().thisBrandCount(brand) < 10) {
            getCarDao().addCar(brand, model, licensePlate, price);
            return true;
        }
        return false;
    }

    public boolean buyCar(String licensePlate) {
        if (getCarDao().isExistsCar(licensePlate)) {
            Long price = getCarDao().getPrice(licensePlate);
            Car car = getCarDao().getCar(licensePlate);
            getCarDao().deleteCar(car);
            Long id = getDailyReportDao().getLastId();
            if (id == null) {
                getDailyReportDao().addNewReport();
                id = getDailyReportDao().getLastId();
            }
            getDailyReportDao().addSoldCarReport(price, id);
            return true;
        }
        return false;
    }

    public void cleanUp() {
        getCarDao().dropTable();
    }

    private CarDao getCarDao() {
        return new CarDao(sessionFactory.openSession());
    }

    private DailyReportDao getDailyReportDao() {
        return new DailyReportDao(sessionFactory.openSession());
    }
}
