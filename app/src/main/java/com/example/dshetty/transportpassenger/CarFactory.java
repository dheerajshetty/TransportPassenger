package com.example.dshetty.transportpassenger;

/**
 * Created by dheerajshetty on 8/31/2015.
 */
public class CarFactory {
    public static Car makeCar(CarType carType, int xPos, int yPos, City city, Passenger passenger) throws Exception {

        Car car = null;
        switch (carType) {
            case SEDAN:
                car = new Sedan(xPos, yPos, city, passenger);
                break;

            case RACE_CAR:
                car = new Sedan(xPos, yPos, city, passenger);
            break;

            default:
                throw new Exception("Unknown car type");
        }

        return car;
    }
}
