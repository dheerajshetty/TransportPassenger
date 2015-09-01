package com.example.dshetty.transportpassenger;


public class Passenger {

    private int __StartingXPos;
    public int getStartingXPos() {
        return __StartingXPos;
    }

    public void setStartingXPos(int value) {
        __StartingXPos = value;
    }

    private int __StartingYPos;
    public int getStartingYPos() {
        return __StartingYPos;
    }

    public void setStartingYPos(int value) {
        __StartingYPos = value;
    }

    private int __DestinationXPos;
    public int getDestinationXPos() {
        return __DestinationXPos;
    }

    public void setDestinationXPos(int value) {
        __DestinationXPos = value;
    }

    private int __DestinationYPos;
    public int getDestinationYPos() {
        return __DestinationYPos;
    }

    public void setDestinationYPos(int value) {
        __DestinationYPos = value;
    }

    private Car __Car;
    public Car getCar() {
        return __Car;
    }

    public void setCar(Car value) {
        __Car = value;
    }

    private City __City;
    public City getCity() {
        return __City;
    }

    public void setCity(City value) {
        __City = value;
    }

    public Passenger(int startXPos, int startYPos, int destXPos, int destYPos, City city) {
        setStartingXPos(startXPos);
        setStartingYPos(startYPos);
        setDestinationXPos(destXPos);
        setDestinationYPos(destYPos);
        setCity(city);
    }

    public void getInCar(Car car) {
        setCar(car);
        car.pickupPassenger(this);
        System.out.println("Passenger got in car.");
    }

    public void getOutOfCar() {
        setCar(null);
    }

    public int getCurrentXPos() {
        if (getCar() == null)
        {
            return getStartingXPos();
        }
        else
        {
            return getCar().getXPos();
        }
    }

    public int getCurrentYPos() {
        if (getCar() == null)
        {
            return getStartingYPos();
        }
        else
        {
            return getCar().getYPos();
        }
    }

    public boolean isAtDestination() {
        return getCurrentXPos() == getDestinationXPos() && getCurrentYPos() == getDestinationYPos();
    }
}
